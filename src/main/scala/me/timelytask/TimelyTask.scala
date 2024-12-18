package me.timelytask

import me.timelytask.controller.{CoreInitializer, ModelController, PersistenceController}
import me.timelytask.model.Model
import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
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
  given modelPublisher: Publisher[Model] = Publisher[Model](Some(Model.default))

  given activeViewPublisher: Publisher[ViewType] = Publisher[ViewType](Some(CALENDAR))
  
  given calendarViewModelPublisher: Publisher[ViewModel[CALENDAR]] = Publisher[ViewModel[CALENDAR]](None)
  
  given taskEditViewModelPublisher: Publisher[ViewModel[TASKEdit]] = Publisher[ViewModel[TASKEdit]](None)

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
  
  
}
