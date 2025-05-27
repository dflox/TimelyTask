package me.timelytask.view.gui

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.model.settings.ViewType
import me.timelytask.util.Publisher
import me.timelytask.view.UIManager
import me.timelytask.view.gui.dialog.DialogFactoryImpl
import me.timelytask.view.views.{CalendarCommonsModule, CalendarView, DialogFactory, TaskEditCommonsModule, TaskEditView}
import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.stage.Stage

class GuiManager(override val activeViewPublisher: Publisher[ViewType],
                 override protected val calendarViewModule: CalendarCommonsModule,
                 override protected val taskEditViewModule: TaskEditCommonsModule)
  extends UIManager[Scene] {
  private var stage: Option[Stage] = None

  override def shutdown(): Unit = {
    stage.foreach(_.close())
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
      }
    }
  }

  override def run(): Unit = {
    calendarView.init()
    taskEditView.init()

    Platform.runLater {
      stage = Some(new Stage {
        title = "TimelyTask GUI"
        scene = new Scene(800, 600)
      })
      stage.foreach(_.show())
    }
  }
}
