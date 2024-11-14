package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Model
import me.timelytask.model.settings.*
import me.timelytask.model.utility.TimeSelection
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.*
import org.mockito.Mockito.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import me.timelytask.model.modelPublisher
import me.timelytask.view.viewmodel.viewModelPublisher

class CalendarControllerSpec extends AnyWordSpec
                             with MockitoSugar with CoreInitializer {

  "The CalendarController" should {

    "handle NextDay action correctly" in {
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      viewModelPublisher.update(calendarViewModel)

      NextDay.call shouldEqual true
      viewModelPublisher.getValue.asInstanceOf[CalendarViewModel].timeSelection.day shouldEqual (timeSelection.day + 1.days)
    }

    "handle PreviousDay action correctly" in {
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      viewModelPublisher.update(calendarViewModel)

      PreviousDay.call shouldEqual true
      viewModelPublisher.getValue.asInstanceOf[CalendarViewModel].timeSelection.day shouldEqual (timeSelection.day - 1.days)
    }

    "handle NextWeek action correctly" in {
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      viewModelPublisher.update(calendarViewModel)

      NextWeek.call shouldEqual true
      viewModelPublisher.getValue.asInstanceOf[CalendarViewModel].timeSelection.day shouldEqual (timeSelection.day + 1.weeks)
    }

    "handle PreviousWeek action correctly" in {
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      viewModelPublisher.update(calendarViewModel)

      PreviousWeek.call shouldEqual true
      viewModelPublisher.getValue.asInstanceOf[CalendarViewModel].timeSelection.day shouldEqual (timeSelection.day - 1.weeks)
    }

    "handle GoToToday action correctly" in {
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      viewModelPublisher.update(calendarViewModel)

      GoToToday.call shouldEqual true
      viewModelPublisher.getValue.asInstanceOf[CalendarViewModel].timeSelection.day shouldEqual DateTime.now().withTimeAtStartOfDay()
    }

    "handle ShowWholeWeek action correctly" in {
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      viewModelPublisher.update(calendarViewModel)

      ShowWholeWeek.call shouldEqual true
      viewModelPublisher.getValue.asInstanceOf[CalendarViewModel].timeSelection.day shouldEqual (timeSelection.currentWeek.day)
    }

    "handle ShowLessDays action correctly" in {
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      viewModelPublisher.update(calendarViewModel)

      ShowLessDays.call shouldEqual true
      viewModelPublisher.getValue.asInstanceOf[CalendarViewModel].timeSelection.day shouldEqual (timeSelection.day - 1.days)
    }

    "handle ShowMoreDays action correctly" in {
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      viewModelPublisher.update(calendarViewModel)

      ShowMoreDays.call shouldEqual true
      viewModelPublisher.getValue.asInstanceOf[CalendarViewModel].timeSelection.day shouldEqual (timeSelection.day + 1.days)
    }
  }
}