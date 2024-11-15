package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.settings.*
import me.timelytask.model.utility.TimeSelection
import me.timelytask.model.{Model, modelPublisher}
import me.timelytask.view.viewmodel.{CalendarViewModel, viewModelPublisher}
import me.timelytask.model.settings.activeViewPublisher

object CalendarController extends Controller {

  val viewModel: () => CalendarViewModel = () => viewModelPublisher.getValue
    .asInstanceOf[CalendarViewModel]

  observe(activeViewPublisher) ({ viewType =>
    if (viewType == ViewType.CALENDAR) {
      viewModelPublisher.update(CalendarViewModel(viewModel().model, TimeSelection
        .defaultTimeSelection))
    } else {
      None
    }
  } , Some(this))

  observe(modelPublisher) ({ model =>
    if (activeViewPublisher.getValue == ViewType.CALENDAR) {
      viewModelPublisher.update(CalendarViewModel(model, viewModel().timeSelection))
    } else {
      None
    }
  }, Some(this))

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

  private def changeTimeSelection(timeSelection: Option[TimeSelection]): Option[CalendarViewModel]
  = {
    timeSelection match {
      case Some(value) => Some(viewModel().copy(timeSelection = value))
      case None => None
    }
  }
}
