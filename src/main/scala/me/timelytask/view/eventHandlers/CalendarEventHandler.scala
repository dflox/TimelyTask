package me.timelytask.view.eventHandlers

import me.timelytask.view.events.{GoToDate, GoToToday, NextDay, NextWeek, PreviousDay, PreviousWeek, ShowLessDays, ShowMoreDays, ShowWholeWeek}
import me.timelytask.view.viewmodel.CalendarViewModel

class CalendarEventHandler(using calendarViewModelPublisher: Publisher[CalendarViewModel]) extends
                                                                 EventHandler[CalendarViewModel] {
  NextDay.setHandler(() => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection + 1.days))
  })

  PreviousDay.setHandler(() => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection - 1.days))
  })

  NextWeek.setHandler(() => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection + 1.weeks))
  })

  PreviousWeek.setHandler(() => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection - 1.weeks))
  })

  GoToToday.setHandler(() => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection.startingToday))
  })

  ShowWholeWeek.setHandler(() => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection.currentWeek))
  })

  ShowMoreDays.setHandler(() => {
    changeTimeSelection(viewModel().timeSelection.addDayCount(1))
  })

  ShowLessDays.setHandler(() => {
    changeTimeSelection(viewModel().timeSelection.subtractDayCount(1))
  })
}
