package me.timelytask.view.gui

import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.*
import me.timelytask.view.views.commonsModules.CalendarCommonsModule
import scalafx.application.Platform
import scalafx.scene.Scene

class GuiCalendarView(override val render: (Scene, ViewType) => Unit,
                      override val dialogFactory: DialogFactory[Scene],
                      viewTypeCommonsModule: CalendarCommonsModule)
  extends CalendarView[Scene]
  with View[CALENDAR, CalendarViewModel, Scene](viewTypeCommonsModule) {

  override def update(viewModel: Option[CalendarViewModel]): Boolean = {
    if viewModel.isEmpty then return false

    val newRootPane = CalendarViewGuiFactory.updateContent(viewModel.get, currentlyRendered, viewTypeCommonsModule)

    Platform.runLater {
      currentlyRendered = currentlyRendered match {
        case Some(existingScene) =>
          existingScene.root = newRootPane
          Some(existingScene)
        case None => val scene = new Scene(newRootPane)
          render(scene, CALENDAR)
          Some(scene)
      }
    }
    true
  }
}