package me.timelytask.view.tui

import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.*

class TuiCalendarView(override val render: (String, ViewType) => Unit,
                      private val tuiModel: Unit => ModelTUI,
                      override val dialogFactory: DialogFactory[String],
                      viewTypeCommonsModule: CalendarCommonsModule)
  extends CalendarView[String] 
  with View[CALENDAR, CalendarViewModel, String](viewTypeCommonsModule) {
  
  override def update(viewModel: Option[CalendarViewModel]): Boolean = {
    if viewModel.isEmpty then return false
    currentlyRendered = Some(CalendarViewStringFactory.buildString(viewModel.get, tuiModel(())))
    render(currentlyRendered.get, CALENDAR)
    true
  }
}
