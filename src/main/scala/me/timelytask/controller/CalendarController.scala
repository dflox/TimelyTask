package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Model
import me.timelytask.model.settings.*
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}

class CalendarController(modelPublisher: Publisher[Model],
                         viewModelPublisher: Publisher[ViewModel])
  extends Controller {
  def handleAction(action: Action): Option[ViewModel] = {
    //handleCalendarAction(action, viewModelPublisher.getCurrentViewModel
    // .asInstanceOf[CalendarViewModel])
    viewModelPublisher.getValue match
      case model: CalendarViewModel => handleCalendarAction(action, model)
      case _ =>
        None
  }

  private def handleCalendarAction(action: Action, viewModel: CalendarViewModel)
  : Option[ViewModel] = {
    action match {
      case NextDay =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection
          .copy(day = viewModel.timeSelection.day + 1.days)))
      case PreviousDay =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection
          .copy(day = viewModel.timeSelection.day - 1.days)))
      case NextWeek =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection
          .copy(day = viewModel.timeSelection.day + 7.days)))
      case PreviousWeek =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection
          .copy(day = viewModel.timeSelection.day - 7.days)))
      case GoToToday =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection
          .copy(day = DateTime.now().withTime(viewModel.timeSelection.day.toLocalTime))))
      case ShowWholeWeek =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection
          .copy(day = viewModel.timeSelection.getFirstDayOfWeek, dayCount = 7)))
      case ShowLessDays =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection
          .copy(dayCount = if ((viewModel.timeSelection.dayCount - 1) < 1)
                             1
                           else
                             viewModel.timeSelection.dayCount - 1)))
      case ShowMoreDays =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection
          .copy(dayCount = if ((viewModel.timeSelection.dayCount + 1) > 14)
                             14
                           else
                             viewModel.timeSelection.dayCount + 1)))
      case _ => None
    }
  }

  def onChange(model: Model): Unit = {

  }
}