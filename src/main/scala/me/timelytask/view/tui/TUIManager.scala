package me.timelytask.view.tui

import me.timelytask.controller.commands.StartApp
import me.timelytask.model.settings.{CALENDAR, TASKEdit, UIType, ViewType}
import me.timelytask.model.utility.{Key, Unknown}
import me.timelytask.util.{CancelableFuture, Publisher}
import me.timelytask.view.UIManager
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.tui.dialog.DialogFactoryTUI
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}
import me.timelytask.view.views.{CalendarView, TaskEditView, View}
import org.jline.keymap.BindingReader
import org.jline.terminal.{Terminal, TerminalBuilder}
import org.jline.utils.InfoCmp.Capability

import java.io.PrintWriter

class TUIManager(override val activeViewPublisher: Publisher[ViewType],
                 override val calendarKeyMapPublisher: Publisher[Keymap[CALENDAR,
                   CalendarViewModel, View[CALENDAR, CalendarViewModel, ?]]],
                 override val calendarViewModelPublisher: Publisher[CalendarViewModel],
                 override val taskEditKeyMapPublisher: Publisher[Keymap[TASKEdit, TaskEditViewModel,
                   View[TASKEdit, TaskEditViewModel, ?]]],
                 override val taskEditViewModelPublisher: Publisher[TaskEditViewModel])
  extends UIManager[String] {
  val uiType: UIType = UIType.TUI

  def init(): Unit = {
    terminal.enterRawMode()
    calendarView.init()
    taskEditView.init()
  }

  val terminal: Terminal = TerminalBuilder.builder()
    .dumb(false)
    .build()

  override val render: (String, ViewType) => Unit = (str: String, vt: ViewType) => {
    if activeViewPublisher.getValue.getOrElse(() => None) == vt then {
      terminal.puts(Capability.clear_screen)
      writer.print(str)
      writer.flush()
    }
  }

  val bindingReader = new BindingReader(terminal.reader())

  def getInput: Key = {
    Option(bindingReader.readBinding(KeyMapManager.keyMap)) match {
      case Some(key) => key
      case None => Unknown
    }
  }

  val createTuiModel: Unit => ModelTUI = unit => if (terminal.getWidth == 0) ModelTUI.default
                                                 else new ModelTUI(terminal.getHeight,
                                                   terminal.getWidth)

  val dialogFactoryTUI: DialogFactoryTUI = new DialogFactoryTUI(terminal)

  val calendarView: CalendarView[String] = new TuiCalendarView(render, createTuiModel,
    calendarKeyMapPublisher, calendarViewModelPublisher, dialogFactoryTUI)

  val taskEditView: TaskEditView[String] = TuiTaskEditView(render, createTuiModel,
    taskEditKeyMapPublisher, taskEditViewModelPublisher, dialogFactoryTUI)

  protected var keyInputTask: Option[CancelableFuture[Key]] = None

  def run(): Unit = {
    init()
    StartApp.createCommand(()).execute
    while true do {
      keyInputTask = Some(
        CancelableFuture(
          task = getInput,
          onSuccess = Some(handleNewKeyInput)
        ))
    }
  }

  def handleNewKeyInput(key: Key): Unit = {
    activeViewPublisher.getValue.get match {
      case CALENDAR => calendarView.handleKey(Some(key))
      case TASKEdit => taskEditView.handleKey(Some(key))
      case _ => ()
    }
  }

  val writer: PrintWriter = terminal.writer()

}
