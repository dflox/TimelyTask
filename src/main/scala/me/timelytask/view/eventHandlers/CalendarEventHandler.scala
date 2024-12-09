package me.timelytask.view.eventHandlers

import me.timelytask.model.utility.TimeSelection
import me.timelytask.view.events.{GoToDate, GoToToday, NextDay, NextWeek, PreviousDay, PreviousWeek, ShowLessDays, ShowMoreDays, ShowWholeWeek}
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.util.Publisher
import com.github.nscala_time.time.Imports.richInt

class CalendarEventHandler(using calendarViewModelPublisher: Publisher[CalendarViewModel]) extends
                                                                 EventHandler[CalendarViewModel] {
  val viewModel: () => CalendarViewModel = () => calendarViewModelPublisher.getValue
    .asInstanceOf[CalendarViewModel]
  
  NextDay.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection + 1.days))
  })

  PreviousDay.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection - 1.days))
  })

  NextWeek.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection + 1.weeks))
  })

  PreviousWeek.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection - 1.weeks))
  })

  GoToToday.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection.startingToday))
  })

  ShowWholeWeek.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection.currentWeek))
  })

  ShowMoreDays.setHandler((args: Unit) => {
    changeTimeSelection(viewModel().timeSelection.addDayCount(1))
  })

  ShowLessDays.setHandler((args: Unit) => {
    changeTimeSelection(viewModel().timeSelection.subtractDayCount(1))
  })

  private def changeTimeSelection(timeSelection: Option[TimeSelection]): Option[CalendarViewModel]
  = {
    timeSelection match {
      case Some(value) => Some(viewModel().copy(timeSelection = value))
      case None => None
    }
  }
}
