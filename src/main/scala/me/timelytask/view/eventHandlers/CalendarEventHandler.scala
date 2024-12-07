package me.timelytask.view.eventHandlers

import me.timelytask.model.utility.TimeSelection
import me.timelytask.view.events.{GoToDate, GoToToday, NextDay, NextWeek, PreviousDay, PreviousWeek, ShowLessDays, ShowMoreDays, ShowWholeWeek}
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.util.Publisher
import com.github.nscala_time.time.Imports.richInt
import me.timelytask.controller.commands.UndoManager
import me.timelytask.model.Model
import me.timelytask.model.settings.{CALENDAR, ViewType}

class CalendarEventHandler(using calendarViewModelPublisher: Publisher[CalendarViewModel],
                           modelPublisher: Publisher[Model],
                           undoManager: UndoManager,
                           activeViewPublisher: Publisher[ViewType]) 
  extends EventHandler[CALENDAR, CalendarViewModel]{
  
  val viewModel: () => CalendarViewModel = () => calendarViewModelPublisher.getValue
    .asInstanceOf[CalendarViewModel]
  
  NextDay.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection + 1.days))
  }, (args: Unit) => None)

  PreviousDay.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection - 1.days))
  }, (args: Unit) => None)

  NextWeek.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection + 1.weeks))
  }, (args: Unit) => None)

  PreviousWeek.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection - 1.weeks))
  }, (args: Unit) => None)

  GoToToday.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection.startingToday))
  }, (args: Unit) => None)

  ShowWholeWeek.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection.currentWeek))
  }, (args: Unit) => None)

  ShowMoreDays.setHandler((args: Unit) => {
    changeTimeSelection(viewModel().timeSelection.addDayCount(1))
  }, (args: Unit) => None)

  ShowLessDays.setHandler((args: Unit) => {
    changeTimeSelection(viewModel().timeSelection.subtractDayCount(1))
  }, (args: Unit) => None)

  private def changeTimeSelection(timeSelection: Option[TimeSelection]): Option[CalendarViewModel]
  = {
    timeSelection match {
      case Some(value) => Some(viewModel().copy(timeSelection = value))
      case None => None
    }
  }
}
