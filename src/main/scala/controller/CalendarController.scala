package controller

import view.*
import model.{Action, *}
import com.github.nscala_time.time.Imports.*
import model.settings.FileType.{JSON, XML}
import view.viewmodel.*
import java.awt.Color
import java.util.UUID
import scala.collection.immutable.{HashMap, HashSet}
import scala.collection.mutable
import io.circe.generic.auto.*
import model.settings.ViewType.TABLE
import model.settings.Theme.DARK
import model.settings.{DataType, FileType, Theme, ViewType}
import view.viewmodel.ViewModel

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