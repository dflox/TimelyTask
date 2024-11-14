package me.timelytask

import me.timelytask.controller.{CalendarController, CoreInitializer, KeyMapManager, PersistenceController, keyMapManager}
import me.timelytask.model.settings.{Exit, StartApp, activeViewPublisher}
import me.timelytask.model.utility.Unknown
import me.timelytask.view.*
import me.timelytask.view.viewmodel.viewModelPublisher
import org.jline.keymap.BindingReader
import org.jline.reader.impl.history.DefaultHistory
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.{Terminal, TerminalBuilder}

object TimelyTask extends App with CoreInitializer {

  //  ------------- Core -------------
  var running = true

  Exit.setHandler(() => {
    running = false
    true
  })
  //  ------------- END Core -------------

  //  ------------- TUI -------------
  given terminal: Terminal = TerminalBuilder.builder()
    .system(true)
    .build()

  val history = new DefaultHistory()
  
  // Create a binding reader for handling key sequences
  val bindingReader = new BindingReader(terminal.reader())

  given inputHandler: InputHandler = new InputHandler

  val windowTUI = new WindowTUI()

  KeyMapManager
  // Enter raw mode to process input immediately
  terminal.enterRawMode()

  terminal.writer().println("starting")
  // ------------- END TUI -------------

  while (running) {
    val key = try {
      Option(bindingReader.readBinding(keyMapManager.keyMap)) match {
        case Some(keyboard) => keyboard
        case None => Unknown
      }
    } catch {
      case _: Exception =>
        Unknown
    }
    windowTUI.onUserInput(key)
  }
}
