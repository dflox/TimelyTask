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

class CalendarControllerSpec extends AnyWordSpec with MockitoSugar {

  "The CalendarController" should {

    "handle default ViewModel correctly" in {
      val modelPublisher = mock[Publisher[Model]]
      val viewModelPublisher = mock[Publisher[ViewModel]]
      val timeSelection = TimeSelection.defaultTimeSelection
      val viewModel = mock[ViewModel]
      when(viewModelPublisher.getValue).thenReturn(viewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val result = controller.handleAction(mock[Action])

      result shouldEqual None
    }

    "handle default action correctly" in {
      val modelPublisher = mock[Publisher[Model]]
      val viewModelPublisher = mock[Publisher[ViewModel]]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getValue).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val result = controller.handleAction(mock[Action])

      result shouldEqual None
    }

    "handle NextDay action correctly" in {
      val modelPublisher = mock[Publisher[Model]]
      val viewModelPublisher = mock[Publisher[ViewModel]]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getValue).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(NextDay).get.asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.day shouldEqual (timeSelection.day + 1.days)
    }
    "handle PreviousDay action correctly" in {
      val modelPublisher = mock[Publisher[Model]]
      val viewModelPublisher = mock[Publisher[ViewModel]]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getValue).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(PreviousDay).get.asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.day shouldEqual (timeSelection.day - 1.days)
    }
    "handle NextWeek action correctly" in {
      val modelPublisher = mock[Publisher[Model]]
      val viewModelPublisher = mock[Publisher[ViewModel]]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getValue).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(NextWeek).get.asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.day shouldEqual (timeSelection.day + 7.days)
    }
    "handle PreviousWeek action correctly" in {
      val modelPublisher = mock[Publisher[Model]]
      val viewModelPublisher = mock[Publisher[ViewModel]]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getValue).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(PreviousWeek).get.asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.day shouldEqual (timeSelection.day - 7.days)
    }
    "handle GoToToday action correctly" in {
      val modelPublisher = mock[Publisher[Model]]
      val viewModelPublisher = mock[Publisher[ViewModel]]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getValue).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(GoToToday).get.asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.day shouldEqual (DateTime.now().withTime(timeSelection.day.toLocalTime))
    }
    "handle ShowWholeWeek action correctly" in {
      val modelPublisher = mock[Publisher[Model]]
      val viewModelPublisher = mock[Publisher[ViewModel]]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getValue).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(ShowWholeWeek).get.asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.day shouldEqual (timeSelection.getFirstDayOfWeek)
    }

    "handle ShowLessDays action correctly" in {
      val modelPublisher = mock[Publisher[Model]]
      val viewModelPublisher = mock[Publisher[ViewModel]]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getValue).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(ShowLessDays).get.asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.dayCount shouldEqual (timeSelection.dayCount - 1)
    }

    "handle ShowMoreDays action correctly" in {
      val modelPublisher = mock[Publisher[Model]]
      val viewModelPublisher = mock[Publisher[ViewModel]]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getValue).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(ShowMoreDays).get.asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.dayCount shouldEqual (timeSelection.dayCount + 1)
    }

    "should have a onModelChange method" in {
      val modelPublisher = mock[Publisher[Model]]
      val viewModelPublisher = mock[Publisher[ViewModel]]
      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      controller.onChange(Model.default)
    }
  }
}