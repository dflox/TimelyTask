package me.timelytask.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.*
import me.timelytask.model.settings.*
import me.timelytask.view.viewmodel.*
import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Model
import me.timelytask.model.utility.TimeSelection

class CalendarControllerSpec extends AnyWordSpec with MockitoSugar {

  "The CalendarController" should {

    "handle default ViewModel correctly" in {
      val modelPublisher = mock[ModelPublisher]
      val viewModelPublisher = mock[ViewModelPublisher]
      val timeSelection = TimeSelection.defaultTimeSelection
      val viewModel = mock[ViewModel]
      when(viewModelPublisher.getCurrentViewModel).thenReturn(viewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val result = controller.handleAction(mock[Action])

      result shouldEqual viewModel
    }

    "handle default action correctly" in {
      val modelPublisher = mock[ModelPublisher]
      val viewModelPublisher = mock[ViewModelPublisher]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getCurrentViewModel).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val result = controller.handleAction(mock[Action])

      result shouldEqual calendarViewModel
    }

    "handle NextDay action correctly" in {
      val modelPublisher = mock[ModelPublisher]
      val viewModelPublisher = mock[ViewModelPublisher]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getCurrentViewModel).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(NextDay).asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.day shouldEqual (timeSelection.day + 1.days)
    }
    "handle PreviousDay action correctly" in {
      val modelPublisher = mock[ModelPublisher]
      val viewModelPublisher = mock[ViewModelPublisher]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getCurrentViewModel).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(PreviousDay).asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.day shouldEqual (timeSelection.day - 1.days)
    }
    "handle NextWeek action correctly" in {
      val modelPublisher = mock[ModelPublisher]
      val viewModelPublisher = mock[ViewModelPublisher]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getCurrentViewModel).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(NextWeek).asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.day shouldEqual (timeSelection.day + 7.days)
    }
    "handle PreviousWeek action correctly" in {
      val modelPublisher = mock[ModelPublisher]
      val viewModelPublisher = mock[ViewModelPublisher]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getCurrentViewModel).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(PreviousWeek).asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.day shouldEqual (timeSelection.day - 7.days)
    }
    "handle GoToToday action correctly" in {
      val modelPublisher = mock[ModelPublisher]
      val viewModelPublisher = mock[ViewModelPublisher]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getCurrentViewModel).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(GoToToday).asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.day shouldEqual (DateTime.now().withTime(timeSelection.day.toLocalTime))
    }
    "handle ShowWholeWeek action correctly" in {
      val modelPublisher = mock[ModelPublisher]
      val viewModelPublisher = mock[ViewModelPublisher]
      val timeSelection = TimeSelection.defaultTimeSelection
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      when(viewModelPublisher.getCurrentViewModel).thenReturn(calendarViewModel)

      val controller = new CalendarController(modelPublisher, viewModelPublisher)
      val updatedViewModel = controller.handleAction(ShowWholeWeek).asInstanceOf[CalendarViewModel]

      updatedViewModel.timeSelection.day shouldEqual (timeSelection.getFirstDayOfWeek)
    }
  }
}