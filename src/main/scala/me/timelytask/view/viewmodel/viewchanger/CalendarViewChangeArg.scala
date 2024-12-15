package me.timelytask.view.viewmodel.viewchanger

import me.timelytask.model.settings.CALENDAR
import me.timelytask.model.utility.TimeSelection
import me.timelytask.view.viewmodel.CalendarViewModel

case class CalendarViewChangeArg(timeSelection: Option[TimeSelection]) 
  extends ViewChangeArgument[CALENDAR, CalendarViewModel] {
  override def apply(viewModel: Option[CalendarViewModel]): Option[CalendarViewModel] = {
    if viewModel.isEmpty | timeSelection.isEmpty then None
    else Some(viewModel.get.copy(timeSelection = timeSelection.get))
  }
}
