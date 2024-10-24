package view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import com.github.nscala_time.time.Imports._
import view.UtilTUI._

class CalendarTUISpec extends AnyWordSpec {

  "The CalendarTUI" should {

    "align the text to the left" in {
      val calendarTUI = new CalendarTUI
      calendarTUI.columnSpacer("Hello", 10, "l") should be("Hello     ")
      calendarTUI.columnSpacer("Hello", 0, "l") should be("Hello")
      calendarTUI.columnSpacer("Hello", 5, "l") should be("Hello")
    }

    // New test case for right alignment (covers line 62)
    "align the text to the right" in {
      val calendarTUI = new CalendarTUI
      calendarTUI.columnSpacer("Hello", 10, "r") should be("     Hello")
      calendarTUI.columnSpacer("Hello", 5, "r") should be("Hello")
      calendarTUI.columnSpacer("Hello", 0, "r") should be("Hello")
    }

    "create a table with the correct format" in {
      val calendarTUI = new CalendarTUI
      val firstDayOfWeek = new DateTime(2024, 10, 14, 0, 0)
      val table = calendarTUI.createTable(firstDayOfWeek, 7, 20, 80)

      // Expected output (simplified example)
      val expectedTable =
        """| Mon 14 | Tue 15 | Wed 16 | Thu 17 | Fri 18 | Sat 19 | Sun 20 |
           |--------|--------|--------|--------|--------|--------|--------|
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
           |        |        |        |        |        |        |        |
        """.stripMargin

      table should include ("Mon 14")
      table should include ("Tue 15")
      table should include ("Wed 16")
      table should include ("Thu 17")
      table should include ("Fri 18")
      table should include ("Sat 19")
      table should include ("Sun 20")
    }

    // New test case for time wrapping (covers line 80)
    "create rows with time wrapping after 24 hours" in {
      val calendarTUI = new CalendarTUI
      val rows = calendarTUI.createRows(22.0, 4.0, 1.0, 7, 8) // Start at 22:00 with 1-hour intervals, 4 hours in total
      rows should include ("22:00")  // First row
      rows should include ("23:00")  // Second row
      rows should include ("00:00")  // Time wrapping after midnight
      rows should include ("01:00")  // Time after midnight
    }

  }
}
