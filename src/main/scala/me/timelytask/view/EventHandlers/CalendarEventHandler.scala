package me.timelytask.view.EventHandlers

class CalendarEventHandler extends EventHandler {
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
