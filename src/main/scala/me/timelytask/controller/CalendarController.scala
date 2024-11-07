package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import io.circe.generic.auto.*
import me.timelytask.model.Model
import me.timelytask.model.settings.{Action, ShowWholeWeek, GoToToday, NextDay, NextWeek, PreviousDay, PreviousWeek}
import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}

import java.awt.Color
import java.util.UUID
import scala.collection.immutable.{HashMap, HashSet}
import scala.collection.mutable

class CalendarController(modelPublisher: ModelPublisher, viewModelPublisher: ViewModelPublisher) extends Controller {
  def handleAction(action: Action): ViewModel = {
    //handleCalendarAction(action, viewModelPublisher.getCurrentViewModel.asInstanceOf[CalendarViewModel])
    viewModelPublisher.getCurrentViewModel match
      case model: CalendarViewModel =>
        handleCalendarAction(action, model)
      case _ =>
        viewModelPublisher.getCurrentViewModel
  }
  
  private def handleCalendarAction(action: Action, viewModel: CalendarViewModel): ViewModel = {
    action match {
      case NextDay =>
        viewModel.copy(timeSelection = viewModel.timeSelection.copy(day = viewModel.timeSelection.day + 1.days))
      case PreviousDay =>
        viewModel.copy(timeSelection = viewModel.timeSelection.copy(day = viewModel.timeSelection.day - 1.days))
      case NextWeek =>
        viewModel.copy(timeSelection = viewModel.timeSelection.copy(day = viewModel.timeSelection.day + 7.days))
      case PreviousWeek =>
        viewModel.copy(timeSelection = viewModel.timeSelection.copy(day = viewModel.timeSelection.day - 7.days))
      case GoToToday =>
        viewModel.copy(timeSelection = viewModel.timeSelection
          .copy(day = DateTime.now().withTime(viewModel.timeSelection.day.toLocalTime)))
      case ShowWholeWeek =>
        viewModel.copy(timeSelection = viewModel.timeSelection
          .copy(day = viewModel.timeSelection.getFirstDayOfWeek, dayCount = 7))
      case _ => viewModel
    }
  }

  def onModelChange(model: Model): Unit = {

  }
}