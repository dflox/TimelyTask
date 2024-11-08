package me.timelytask

import me.timelytask.controller.*
import me.timelytask.model.*
import me.timelytask.model.settings.ViewType
import me.timelytask.model.settings.ViewType.CALENDAR
import me.timelytask.model.utility.*
import me.timelytask.util.Publisher
import me.timelytask.view.*
import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}
import org.jline.keymap.BindingReader
import org.jline.reader.impl.history.DefaultHistory
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.{Terminal, TerminalBuilder}

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
    val viewModelPublisher = new Publisher[ViewModel](new CalendarViewModel(model, TimeSelection.defaultTimeSelection))
    val modelPublisher = new Publisher[Model](model)
    val activeViewPublisher = new Publisher[ViewType](CALENDAR)
    val persistenceController = new PersistenceController(viewModelPublisher, modelPublisher, activeViewPublisher)
    val controller = new CalendarController(modelPublisher, viewModelPublisher)
    val inputHandler = new InputHandler(keyMapManager,
      InputHandler.getControllerMap(controller, persistenceController), viewModelPublisher)
    val viewManager = new ViewManager(viewModelPublisher)
    
    
    val window = new Window(terminal, inputHandler, viewManager)

    activeViewPublisher.update(ViewType.CALENDAR)
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
