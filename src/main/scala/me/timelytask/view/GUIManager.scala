package me.timelytask.view.gui

import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.UIManager
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.gui.{CalendarGUI, TaskDetailsView}
import me.timelytask.view.viewmodel.dialogmodel.OptionDialogModel
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}
import me.timelytask.view.views.{CalendarView, TaskEditView}
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ToolBar}
import scalafx.scene.layout.{BorderPane, VBox}
import scalafx.geometry.Pos

class GUIManager(using override val activeViewPublisher: Publisher[ViewType],
                 override val calendarKeyMapPublisher: Publisher[Keymap[CALENDAR, CalendarViewModel, CalendarView[?]]],
                 override val calendarViewModelPublisher: Publisher[CalendarViewModel],
                 override val taskEditKeyMapPublisher: Publisher[Keymap[TASKEdit, TaskEditViewModel, TaskEditView[?]]],
                 override val taskEditViewModelPublisher: Publisher[TaskEditViewModel])
  extends UIManager[Unit] with JFXApp3 {

  override val render: (Unit, ViewType) => Unit = (_, vt: ViewType) => {
    if activeViewPublisher.getValue == vt then {
      updateView(vt)
    }
  }

  val calendarView: CalendarView[Unit] = new CalendarView[Unit] {
    override def renderOptionDialog(optionDialogModel: Option[OptionDialogModel[Task]], renderType: Option[Unit]): Option[Task] = None
  }

  val taskEditView: TaskEditView[Unit] = new TaskEditView[Unit] {
    override def renderOptionDialog(optionDialogModel: Option[OptionDialogModel[Task]], renderType: Option[Unit]): Option[Task] = None
  }

  def createGuiModel: Unit => ModelGUI = _ => ModelGUI()

  def updateView(viewType: ViewType): Unit = {
    viewType match {
      case CALENDAR => calendarViewModelPublisher.getValue.foreach(calendarView.update)
      case TASKEdit => taskEditViewModelPublisher.getValue.foreach(taskEditView.update)
    }
  }

  override def run(): Unit = {
    start()
  }

  override def start(): Unit = {
    stage = new PrimaryStage {
      title = "Calendar View"
      scene = new Scene {
        root = new CalendarGUI().createCalendarGUI()
      }
    }
    stage.show()
  }
}

case class ModelGUI()