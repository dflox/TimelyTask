package me.timelytask.view.views.viewImpl.gui

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.commonsModules.{CalendarCommonsModule}
import me.timelytask.view.views.viewImpl.gui.GuiCalendarView
import me.timelytask.view.views.viewImpl.gui.dialog.DialogFactoryImpl
import me.timelytask.view.views.{CalendarView, DialogFactory, UIManager}
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox
import scalafx.stage.Stage
import scalafx.scene.image.Image

class GuiManager(override val activeViewPublisher: Publisher[ViewType],
                 override protected val calendarViewModule: CalendarCommonsModule
//                 ,
//                 override protected val taskEditViewModule: TaskEditCommonsModule
                 )
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
//  override val taskEditView: TaskEditView[Scene] = wireWith[GuiTaskEditView](() =>
//    GuiTaskEditView(render, dialogFactory, taskEditViewModule))

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
      val initialScene = new Scene(300, 300) {
        root = new VBox {
          alignment = Pos.Center
          children = Seq(
            Label("Bitte warten...")
          )
        }
      }
      calendarView.render(initialScene, CALENDAR)      
      
      stage = Some(new Stage {
        title = "TimelyTask - " + calendarViewModule.globalEventContainer.userToken // bit hacky, but works
        scene = initialScene
        icons += new Image("/icons/app_icon.png")
        onCloseRequest = _ => {
          calendarViewModule.globalEventContainer.closeInstance()
        }
      })
      stage.foreach(_.show())
      calendarView.init()
//      taskEditView.init()
    }

  }
}
