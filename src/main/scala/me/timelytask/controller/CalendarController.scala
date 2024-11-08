package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import io.circe.generic.auto.*
import me.timelytask.model.Model
import me.timelytask.model.settings.{Action, GoToToday, NextDay, NextWeek, PreviousDay, PreviousWeek, ShowLessDays, ShowMoreDays, ShowWholeWeek}
import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}

import java.awt.Color
import java.util.UUID
import scala.collection.immutable.{HashMap, HashSet}
import scala.collection.mutable

class CalendarController(modelPublisher: ModelPublisher, viewModelPublisher: ViewModelPublisher) extends Controller {
  def handleAction(action: Action): Option[ViewModel] = {
    //handleCalendarAction(action, viewModelPublisher.getCurrentViewModel.asInstanceOf[CalendarViewModel])
    viewModelPublisher.getCurrentViewModel match
      case model: CalendarViewModel =>
        handleCalendarAction(action, model)
      case _ =>
        None
  }
  
  private def handleCalendarAction(action: Action, viewModel: CalendarViewModel): Option[ViewModel] = {
    action match {
      case NextDay =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection.copy(day = viewModel.timeSelection.day + 1.days)))
      case PreviousDay =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection.copy(day = viewModel.timeSelection.day - 1.days)))
      case NextWeek =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection.copy(day = viewModel.timeSelection.day + 7.days)))
      case PreviousWeek =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection.copy(day = viewModel.timeSelection.day - 7.days)))
      case GoToToday =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection
          .copy(day = DateTime.now().withTime(viewModel.timeSelection.day.toLocalTime))))
      case ShowWholeWeek =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection
          .copy(day = viewModel.timeSelection.getFirstDayOfWeek, dayCount = 7)))
      case ShowLessDays =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection.copy(dayCount = viewModel.timeSelection.dayCount - 1)))
      case ShowMoreDays =>
        Some(viewModel.copy(timeSelection = viewModel.timeSelection.copy(dayCount = viewModel.timeSelection.dayCount + 1)))
      case _ => None
    }
  }

  def onModelChange(model: Model): Unit = {
    
  }
}