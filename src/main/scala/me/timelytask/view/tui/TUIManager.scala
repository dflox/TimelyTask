package me.timelytask.view.tui

import com.softwaremill.macwire.wire
import me.timelytask.controller.commands.StartApp
import me.timelytask.model.settings.{CALENDAR, TASKEdit, UIType, ViewType}
import me.timelytask.model.utility.{Key, Unknown}
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.util.{CancelableFuture, Publisher}
import me.timelytask.view.UIManager
import me.timelytask.view.eventHandlers.{CalendarEventContainer, TaskEditEventContainer}
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.tui.dialog.DialogFactoryTUI
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}
import me.timelytask.view.views.*
import org.jline.keymap.BindingReader
import org.jline.terminal.{Terminal, TerminalBuilder}
import org.jline.utils.InfoCmp.Capability

import java.io.PrintWriter

class TUIManager(override val activeViewPublisher: Publisher[ViewType],
                 override protected val calendarViewModule: CalendarCommonsModule,
                 override protected val taskEditViewModule: TaskEditCommonsModule
                )
  extends UIManager[String] {

  override def shutdown(): Unit = {
    keyInputTask.foreach(_.cancel())
    terminal.puts(Capability.clear_screen)
    terminal.flush()
    terminal.close()
  }

  private def init(): Unit = {
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

  val dialogFactoryTUI: DialogFactoryTUI = wire[DialogFactoryTUI]

  val calendarView: CalendarView[String] = wire[TuiCalendarView]

  val taskEditView: TaskEditView[String] = wire[TuiTaskEditView]

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
