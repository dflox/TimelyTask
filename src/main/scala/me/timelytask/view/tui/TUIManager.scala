package me.timelytask.view.tui

import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
import me.timelytask.model.utility.{Key, Unknown}
import me.timelytask.util.{ApplicationThread, CancelableTask, Publisher}
import me.timelytask.view.UIManager
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}
import me.timelytask.view.views.{CalendarView, TaskEditView, View}
import org.jline.keymap.BindingReader
import org.jline.terminal.{Terminal, TerminalBuilder}
import org.jline.utils.InfoCmp.Capability

import java.io.PrintWriter

class TUIManager(using override val activeViewPublisher: Publisher[ViewType],
                 override val calendarKeyMapPublisher: Publisher[Keymap[CALENDAR,
                   CalendarViewModel, CalendarView[?]]],
                 override val calendarViewModelPublisher: Publisher[CalendarViewModel],
                 override val taskEditKeyMapPublisher: Publisher[Keymap[TASKEdit, TaskEditViewModel,
                   TaskEditView[?]]],
                 override val taskEditViewModelPublisher: Publisher[TaskEditViewModel])
  extends UIManager[String] {

  val terminal: Terminal = TerminalBuilder.builder()
    .system(true)
    .build()

  terminal.enterRawMode()

  override val render: (String, ViewType) => Unit = (str: String, vt: ViewType) => {
    if activeViewPublisher.getValue == vt then {
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

  val createTuiModel: Unit => ModelTUI = unit => ModelTUI(terminal.getHeight, terminal.getWidth)

  val calendarView: CalendarView[String] = TuiCalendarView(render, createTuiModel)

  val taskEditView: TaskEditView[String] = TuiTaskEditView(render, createTuiModel)

  val inputThread = new ApplicationThread[Key]()

  protected var keyInputTask: Option[CancelableTask[Key]] = None

  def run(): Unit = {
    while true do {
      keyInputTask = Some(inputThread.run(getInput))
      val key = keyInputTask.get.await()
      activeViewPublisher.getValue match {
        case CALENDAR => calendarView.handleKey(Some(key))
        case TASKEdit => taskEditView.handleKey(Some(key))
      }
    }
  }

  val writer: PrintWriter = terminal.writer()

}
