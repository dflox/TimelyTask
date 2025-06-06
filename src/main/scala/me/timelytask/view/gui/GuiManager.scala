package me.timelytask.view.gui

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.gui.dialog.DialogFactoryImpl
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.commonsModules.{CalendarCommonsModule, TaskEditCommonsModule}
import me.timelytask.view.views.{CalendarView, DialogFactory, TaskEditView, UIManager}
import scalafx.application.Platform
import scalafx.Includes.*
import scalafx.scene.Scene
import scalafx.stage.Stage

class GuiManager(override val activeViewPublisher: Publisher[ViewType],
                 override protected val calendarViewModule: CalendarCommonsModule,
                 override protected val taskEditViewModule: TaskEditCommonsModule)
  extends UIManager[Scene] {
  private var stage: Option[Stage] = None

  override def shutdown(afterShutdownAction: () => Unit = () => ()): Unit = {
    Platform.runLater {
      stage.foreach(_.close())
    }
    afterShutdownAction()
  }

  private val dialogFactory: DialogFactory[Scene] = wire[DialogFactoryImpl]

  override val calendarView: CalendarView[Scene] = wireWith[GuiCalendarView](() =>
    GuiCalendarView(render, dialogFactory, calendarViewModule))
  override val taskEditView: TaskEditView[Scene] = wireWith[GuiTaskEditView](() =>
    GuiTaskEditView(render, dialogFactory, taskEditViewModule))

  override def render(scene: Scene, viewType: ViewType): Unit = {
    if activeViewPublisher.getValue.contains(viewType) then {
      Platform.runLater {
        stage.foreach(_.setScene(scene))
        stage.foreach(_.sizeToScene())
        stage.foreach(_.centerOnScreen())
      }
    }
  }

  override def run(): Unit = {
    Platform.runLater {
      val initialScene = new Scene(300, 300)
      calendarView.render(initialScene, CALENDAR)      
      
      stage = Some(new Stage {
        title = "TimelyTask"
        scene = initialScene
        onCloseRequest = _ => {
          calendarViewModule.globalEventContainer.closeInstance()
        }
      })
      stage.foreach(_.show())
      calendarView.init()
      taskEditView.init()
    }

  }
}
