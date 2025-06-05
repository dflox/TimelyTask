package me.timelytask.view.tui

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
import me.timelytask.model.utility.{Key, Unknown}
import me.timelytask.util.{CancelableFuture, Publisher}
import me.timelytask.view.UIManager
import me.timelytask.view.tui.dialog.DialogFactoryTUI
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

  override def shutdown(afterShutdownAction: () => Unit = () => ()): Unit = {
    stopInput()
    terminal.puts(Capability.clear_screen)
    terminal.flush()
    terminal.close()
    afterShutdownAction()
  }

  private def init(): Unit = {
    terminal.enterRawMode()
    calendarView.init()
    taskEditView.init()
  }

  val terminal: Terminal = TerminalBuilder.builder()
    .dumb(false)
    .build()

  override def render(str: String, vt: ViewType): Unit = {
    if activeViewPublisher.getValue.contains(vt) then {
      stopInput()
      terminal.puts(Capability.clear_screen)
      writer.print(str)
      writer.flush()
      startInput()
    }
  }

  private val bindingReader = new BindingReader(terminal.reader())

  private val writer: PrintWriter = terminal.writer()

  private val createTuiModel: Unit => ModelTUI = unit => if (terminal.getWidth == 0) ModelTUI.default
                                                 else new ModelTUI(terminal.getHeight,
                                                   terminal.getWidth)

  private lazy val dialogFactoryTUI: DialogFactoryTUI = wire[DialogFactoryTUI]

  val calendarView: CalendarView[String] = wireWith[TuiCalendarView](
    () => new TuiCalendarView(render, createTuiModel, dialogFactoryTUI, calendarViewModule))

  val taskEditView: TaskEditView[String] = wireWith[TuiTaskEditView](
    () => new TuiTaskEditView(render, createTuiModel, dialogFactoryTUI, taskEditViewModule))

  private var keyInputTask: Option[CancelableFuture[Unit]] = None

  def run(): Unit = {
    init()
  }

  private def stopInput(): Unit = {
    keyInputTask.foreach(_.cancel())
  }

  private def startInput(): Unit = {
    keyInputTask = Some(CancelableFuture(task = getInput()))
  }

  private def getInput(): Unit = {
    while (true) {
      handleNewKeyInput(getNextKey)
    }
  }

  private def getNextKey: Key = {
    Option(bindingReader.readBinding(KeyMapManager.keyMap)) match {
      case Some(key) => key
      case None => Unknown
    }
  }

  private def handleNewKeyInput(key: Key): Unit = {
    activeViewPublisher.getValue.get match {
      case CALENDAR => calendarView.handleKey(Some(key))
      case TASKEdit => taskEditView.handleKey(Some(key))
      case _ => ()
    }
  }
}
