package me.timelytask.view.gui

import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.*
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane

class GuiCalendarView(override val render: (Scene, ViewType) => Unit,
                      override val dialogFactory: DialogFactory[Scene],
                      viewTypeCommonsModule: CalendarCommonsModule)
  extends CalendarView[Scene]
    with View[CALENDAR, CalendarViewModel, Scene](viewTypeCommonsModule) {

  private val rootPane = new BorderPane()
  private val scene = new Scene(rootPane)
  currentlyRendered = Some(scene)

  override def update(viewModel: Option[CalendarViewModel]): Boolean = {
    if viewModel.isEmpty then return false

    CalendarViewGuiFactory.updateContent(viewModel.get, rootPane)

    render(currentlyRendered.get, CALENDAR)
    true
  }
}