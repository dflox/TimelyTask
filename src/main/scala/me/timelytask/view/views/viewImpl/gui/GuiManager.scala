package me.timelytask.view.views.viewImpl.gui

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.commonsModules.{CalendarCommonsModule, TaskEditCommonsModule}
import me.timelytask.view.views.viewImpl.gui.GuiCalendarView
import me.timelytask.view.views.viewImpl.gui.dialog.DialogFactoryImpl
import me.timelytask.view.views.{CalendarView, DialogFactory, TaskEditView, UIManager}
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox
import scalafx.stage.Stage

class GuiManager(override val activeViewPublisher: Publisher[ViewType],
                 override protected val calendarViewModule: CalendarCommonsModule,
                 override protected val taskEditViewModule: TaskEditCommonsModule)
  extends UIManager[Scene] {
  private var _stage: Option[Stage] = None

  override val stage: Option[Stage] = _stage

  override def shutdown(afterShutdownAction: () => Unit = () => ()): Unit = {
    Platform.runLater {
      _stage.foreach(_.close())
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
        _stage.foreach(_.setScene(scene))
        _stage.foreach(_.sizeToScene())
        _stage.foreach(_.centerOnScreen())
      }
    }
  }

  override def run(): Unit = {
    Platform.runLater {
      val initialScene = new Scene(300, 300) {
        root = new VBox {
          alignment = Pos.Center
          children = Seq(
            Label("Bitte warten...")
          )
        }
      }
      calendarView.render(initialScene, CALENDAR)      
      
      _stage = Some(new Stage {
        title = "TimelyTask"
        scene = initialScene
        onCloseRequest = _ => {
          calendarViewModule.globalEventContainer.closeInstance()
        }
      })
      _stage.foreach(_.show())
      calendarView.init()
      taskEditView.init()
    }

  }
}
