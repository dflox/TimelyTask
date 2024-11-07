package me.timelytask.view.tui

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.utility.TimeSelection
import me.timelytask.model.{Model, Task}
import me.timelytask.view.tui.CalendarTUI
import me.timelytask.view.viewmodel.{CalendarViewModel, TUIModel, ViewModel}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class CalendarTUISpec extends AnyWordSpec {

  "The CalendarTUI" should {

    "update the view correctly" in {
      val fixedDateTime = new DateTime(2024, 11, 6, 16, 55)
      val timeSelection = TimeSelection(fixedDateTime, 7, 1.hour)
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      val viewModel: ViewModel = calendarViewModel
      val output = CalendarTUI.update(viewModel)

      output should include("-------------------------------------------------------------------------------\nCalendar                                                    06. - 12. Nov. 2024\n-------------------------------------------------------------------------------\n| Time  | Mi. 06  | Do. 07  | Fr. 08  | Sa. 09  | So. 10  | Mo. 11  | Di. 12  |\n-------------------------------------------------------------------------------\n| 16:55 |         |         |         |         |         |         |         |\n| 17:10 |         |         |         |         |         |         |         |\n| 17:25 |         |         |         |         |         |         |         |\n| 17:40 |         |         |         |         |         |         |         |\n-------------------------------------------------------------------------------\n\n\n\n\n\n\n\n\n\n\n\n\n")
    }

    "update the view correctly again" in {
      val fixedDateTime = new DateTime(2024, 11, 6, 16, 55)
      val timeSelection = TimeSelection(fixedDateTime, 7, 1.hour)
      val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
      val viewModel: ViewModel = calendarViewModel
      val tuiModel = TUIModel.default
      val output = CalendarTUI.update(viewModel, tuiModel)

      output should include ("-------------------------------------------------------------------------------\nCalendar                                                    06. - 12. Nov. 2024\n-------------------------------------------------------------------------------\n| Time  | Mi. 06  | Do. 07  | Fr. 08  | Sa. 09  | So. 10  | Mo. 11  | Di. 12  |\n-------------------------------------------------------------------------------\n| 16:55 |         |         |         |         |         |         |         |\n| 17:10 |         |         |         |         |         |         |         |\n| 17:25 |         |         |         |         |         |         |         |\n| 17:40 |         |         |         |         |         |         |         |\n-------------------------------------------------------------------------------\n\n\n\n\n\n\n\n\n\n\n\n\n")
    }

    "align the text to the left" in {
      CalendarTUI.columnSpacer("Hello", 10, "l") should be("Hello     ")
      CalendarTUI.columnSpacer("Hello", 5, "l") should be("Hello")
      CalendarTUI.columnSpacer("Hello", 0, "l") should be("Hello")
    }

    "align the text to the middle" in {
      CalendarTUI.columnSpacer("Hello", 10, "m") should be("  Hello   ")
      CalendarTUI.columnSpacer("Hello", 5, "m") should be("Hello")
      CalendarTUI.columnSpacer("Hello", 0, "m") should be("Hello")
    }

    "align the text to the right" in {
      CalendarTUI.columnSpacer("Hello", 10, "r") should be("     Hello")
      CalendarTUI.columnSpacer("Hello", 5, "r") should be("Hello")
      CalendarTUI.columnSpacer("Hello", 0, "r") should be("Hello")
    }

    "header should be correct" in {
      val timeSelection = TimeSelection.defaultTimeSelection
      val header = CalendarTUI.header(80, timeSelection)
      header should include ("Calendar")
      header should include (DateTime.now().withPeriodAdded((timeSelection.dayCount-1).days, 1).toString("dd. MMM yyyy"))
    }

    // New test case for time wrapping (covers line 80)
    "create rows with time wrapping after 24 hours" in {
      val timeSelection = TimeSelection(new DateTime(2024, 10, 14, 22, 0), 2, 5.hour)
      val tasks = List(Task.exampleTask)
      val spacePerColumn = Task.exampleTask.name.length + 2
      val rows = CalendarTUI.createRows(1.hour, 5, timeSelection, tasks, spacePerColumn)
      rows should include ("22:00")  // First row
      rows should include ("23:00")  // Second row
      rows should include ("00:00")  // Time wrapping after midnight
      rows should include ("01:00")  // Time after midnight
      rows should not include Task.exampleTask.name

      val timeSelection2 = TimeSelection(DateTime.now().withPeriodAdded(2.hour, -1), 2, 5.hour)
      val rows2 = CalendarTUI.createRows(1.hour, 5, timeSelection2, tasks, spacePerColumn)
      rows2 should include (Task.exampleTask.name)
    }

    "calculate the intervals that can be displayed with regard to the provided time frame" in {
      val (timeSlice, lines) = CalendarTUI.calculatePeriod(22, new Interval(new DateTime(2024, 10, 14, 7, 0), new DateTime(2024, 10, 14, 19, 0)))
      lines should be(12)
      timeSlice.toString should be("PT1H")
    }

  }
}
