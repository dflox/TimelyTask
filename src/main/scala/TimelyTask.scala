import controller.*
import view.*
import model.*
import org.jline.terminal.{Terminal, TerminalBuilder}

import scala.collection.immutable.HashSet

object TimelyTask {
  //val model: Model = new Model(controller)
  def main(args: Array[String]): Unit = {
    val terminal: Terminal = TerminalBuilder.builder()
      .system(true) // Use the system terminal
      .jansi(true) // Enable ANSI support for colors, if needed
      .build()
    
    val keyMapManager = new KeyMapManager()
    val model = Model.default;
    val viewModelPublisher = new ViewModelPublisher(new CalendarViewModel(model, TimeSelection.defaultTimeSelection))
    val modelPublisher = new ModelPublisher(model)
    val controller = new CalendarController(modelPublisher, viewModelPublisher)
    val inputHandler = new InputHandler(keyMapManager, InputHandler.getControllerMap(controller), viewModelPublisher)
    val viewManager = new ViewManager(viewModelPublisher)
    val activeViewPublisher = new ActiveViewPublisher()
    
    val window = new Window(terminal, inputHandler, viewManager)
    
    // Enter raw mode to process input immediately
    terminal.enterRawMode()

    val reader = terminal.reader() // Get the terminal reader
    var running = true
    
    terminal.writer().println("starting")
    window.updateView()
    while (running) {
      val input = reader.read() // Read a single character
      window.onUserInput(input)
    }
    
  }
}
