package view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import com.github.nscala_time.time.Imports.*
import model.{Task, TimeSelection}
import view.tui.CalendarTUI

class CalendarTUISpec extends AnyWordSpec {

  "The CalendarTUI" should {

    "align the text to the left" in {
      CalendarTUI.columnSpacer("Hello", 10, "l") should be("Hello     ")
      CalendarTUI.columnSpacer("Hello", 5, "l") should be("Hello")
      CalendarTUI.columnSpacer("Hello", 0, "l") should be("Hello")
    }

    "header should be correct" in {
      val timeSelection = TimeSelection.defaultTimeSelection
      val header = CalendarTUI.header(80, timeSelection)
      header should include ("Calendar")
      header should include (DateTime.now().withPeriodAdded((timeSelection.dayCount-1).days, 1).toString("dd. MMM yyyy"))
    }

    "align the text to the right" in {
      CalendarTUI.columnSpacer("Hello", 10, "r") should be("     Hello")
      CalendarTUI.columnSpacer("Hello", 5, "r") should be("Hello")
      CalendarTUI.columnSpacer("Hello", 0, "r") should be("Hello")
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
