package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import io.circe.generic.auto.*
import me.timelytask.model.Model
import me.timelytask.model.settings.{Action, GoToToday, NextDay, NextWeek, PreviousDay, PreviousWeek}
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
  
  private def handleCalendarAction(action: Action, model: CalendarViewModel): ViewModel = {
    action match {
      case NextDay =>
        model.copy(timeSelection = model.timeSelection.copy(day = model.timeSelection.day + 1.days))
      case PreviousDay =>
        model.copy(timeSelection = model.timeSelection.copy(day = model.timeSelection.day - 1.days))
      case NextWeek =>
        model.copy(timeSelection = model.timeSelection.copy(day = model.timeSelection.day + 7.days))
      case PreviousWeek =>
        model.copy(timeSelection = model.timeSelection.copy(day = model.timeSelection.day - 7.days))
      case GoToToday =>
        model.copy(timeSelection = model.timeSelection
          .copy(day = DateTime.now().withTime(model.timeSelection.day.toLocalTime)))
      case _ => model
    }
  }

  def onModelChange(model: Model): Unit = {

  }
}