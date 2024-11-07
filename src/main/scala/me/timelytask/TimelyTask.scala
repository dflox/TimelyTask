package me.timelytask

import controller.*
import model.*
import model.settings.ViewType
import model.utility.*
import org.jline.keymap.BindingReader
import org.jline.reader.impl.history.DefaultHistory
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.{Terminal, TerminalBuilder}
import view.*
import view.viewmodel.CalendarViewModel

import scala.collection.immutable.HashSet

object TimelyTask {
  def exit(): Unit = {
    running = false
  }
  var running = true
  
  def main(args: Array[String]): Unit = {
    val terminal: Terminal = TerminalBuilder.builder()
      .system(true)
      .build()

    // Create a new history object that we can clear
    val history = new DefaultHistory()

    val reader: LineReader = LineReaderBuilder.builder()
      .terminal(terminal)
      .history(history) // Attach the history
      .variable(LineReader.HISTORY_SIZE, 0) // Disable history size
      .build()
    
    // Create a binding reader for handling key sequences
    val bindingReader = new BindingReader(terminal.reader())
    
    val keyMapManager = new KeyMapManager()
    val model = Model.default;
    val viewModelPublisher = new ViewModelPublisher(new CalendarViewModel(model, TimeSelection.defaultTimeSelection))
    val modelPublisher = new ModelPublisher(model)
    val activeViewPublisher = new ActiveViewPublisher()
    val persistenceController = new PersistenceController(viewModelPublisher, modelPublisher, activeViewPublisher)
    val controller = new CalendarController(modelPublisher, viewModelPublisher)
    val inputHandler = new InputHandler(keyMapManager, InputHandler.getControllerMap(controller, persistenceController), viewModelPublisher)
    val viewManager = new ViewManager(viewModelPublisher)
    
    
    val window = new Window(terminal, inputHandler, viewManager)

    activeViewPublisher.updateActiveView(ViewType.CALENDAR)
    viewModelPublisher.subscribe(window)
    modelPublisher.subscribe(controller)
    activeViewPublisher.subscribe(keyMapManager)
        
    // Enter raw mode to process input immediately
    terminal.enterRawMode()
    
    terminal.writer().println("starting")
    window.updateView()
    while (running) {
      val key = try {
        Option(bindingReader.readBinding(KeyMapManager.keyMap)) match {
          case Some(keyboard) => keyboard
          case None => Unknown
        }
      } catch {
        case _: Exception =>
          Unknown
      }
      window.onUserInput(key)
    }
    
  }
}
