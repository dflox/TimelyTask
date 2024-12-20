package me.timelytask.view.gui

import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.UIManager
import me.timelytask.view.gui.dialog.DialogFactoryGUI
import me.timelytask.view.gui.{CalendarViewSceneFactory, TaskDetailsView}
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.dialogmodel.OptionDialogModel
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}
import me.timelytask.view.views.{CalendarView, TaskEditView}
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ToolBar}
import scalafx.scene.layout.{BorderPane, VBox}

class GUIManager(using override val activeViewPublisher: Publisher[ViewType],
                 override val calendarKeyMapPublisher: Publisher[Keymap[CALENDAR, CalendarViewModel, CalendarView[?]]],
                 override val calendarViewModelPublisher: Publisher[CalendarViewModel],
                 override val taskEditKeyMapPublisher: Publisher[Keymap[TASKEdit, TaskEditViewModel, TaskEditView[?]]],
                 override val taskEditViewModelPublisher: Publisher[TaskEditViewModel])
  extends UIManager[Unit] with JFXApp3 {

  override val render: (Scene, ViewType) => Unit = (sc: Scene, vt: ViewType) => {
    if activeViewPublisher.getValue.getOrElse(() => None) == vt then {
        stage.scene = sc
    }
  }
  
  given dialogFactory: DialogFactoryGUI = DialogFactoryGUI()

  val calendarView: GUICalendarView = new GUICalendarView(render)

  val taskEditView: GUITaskEditView = new GUITaskEditView(render)

  def createGuiModel: Unit => ModelGUI = _ => ModelGUI()

  override def run(): Unit = {
    start()
  }

  override def start(): Unit = {
    stage = new PrimaryStage {
    }
    render(new CalendarViewSceneFactory().createCalendarGUI(), CALENDAR)
    stage.show()
  }
}

case class ModelGUI()