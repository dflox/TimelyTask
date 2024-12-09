package me.timelytask

import me.timelytask.controller.{CoreInitializer, PersistenceController, ModelController, keyMapManager}
import me.timelytask.model.Model
import me.timelytask.model.settings.{Exit, StartApp, ViewType}
import me.timelytask.model.utility.Unknown
import me.timelytask.util.Publisher
import me.timelytask.view.*
import me.timelytask.view.tui.KeyMapManager
import me.timelytask.view.viewmodel.ViewModel
import org.jline.keymap.BindingReader
import org.jline.reader.impl.history.DefaultHistory
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.{Terminal, TerminalBuilder}

object TimelyTask extends App {

  //  ------------- Core -------------
  var running = true

  Exit.setHandler(() => {
    running = false
    true
  })
  given modelPublisher: Publisher[Model] = Publisher[Model](Model.default)

  given activeViewPublisher: Publisher[ViewType] = Publisher[ViewType](ViewType.CALENDAR)
  
  given viewModelPublisher: Publisher[ViewModel] = Publisher[ViewModel](DefaultViewModelProvider
    .defaultViewModel)

  given taskController: ModelController = new ModelController()
  
  println("CoreInitializer")
  given persistenceController: PersistenceController = new PersistenceController()
  
  summon[ModelController]
  summon[PersistenceController]
  //  ------------- END Core -------------

  //  ------------- TUI -------------
  given terminal: Terminal = TerminalBuilder.builder()
    .system(true)
    .build()

  val history = new DefaultHistory()
  
  // Create a binding reader for handling key sequences
  val bindingReader = new BindingReader(terminal.reader())

  given inputHandler: KeyInputHandler = new KeyInputHandler

  val windowTUI = new WindowTUI()

  KeyMapManager
  // Enter raw mode to process input immediately
  terminal.enterRawMode()

  terminal.writer().println("starting")
  // ------------- END TUI -------------

  StartApp.call
  
  while (running) {
    val key = Option(bindingReader.readBinding(keyMapManager.keyMap)) match {
        case Some(keyboard) => keyboard
        case None => Unknown
      }
    windowTUI.onUserInput(key)
  }
}
