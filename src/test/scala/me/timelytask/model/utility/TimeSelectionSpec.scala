package me.timelytask.model.utility

import com.github.nscala_time.time.Imports.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TimeSelectionSpec extends AnyWordSpec with Matchers {

  // A Fixture to set up a predictable, fixed TimeSelection for tests.
  // Using a fixed date makes all calculations repeatable.
  // October 26, 2023, is a Thursday.
  trait Fixture {
    val fixedDate: DateTime = new DateTime(2023, 10, 26, 10, 0) // A Thursday at 10:00
    // Use .toPeriod to match the case class's type
    val timeSelection: TimeSelection = TimeSelection(fixedDate, dayCount = 3, timeFrame = 8.hours.toPeriod)
  }

  "A TimeSelection" should {

    "be constructed correctly and compute its interval" in new Fixture {
      timeSelection.day should be(fixedDate)
      timeSelection.dayCount should be(3)
      // Assert against a Period, not a DurationBuilder
      timeSelection.timeFrame should be(8.hours.toPeriod)

      // The timeFrameInterval should start at the day and last for the timeFrame period.
      val expectedInterval = new Interval(fixedDate, fixedDate.plus(8.hours.toPeriod))
      timeSelection.timeFrameInterval should be(expectedInterval)
    }

    "getFirstDayOfWeek should calculate the Monday of the current week" in new Fixture {
      // Thursday Oct 26th -> Monday Oct 23rd
      val expectedMonday = new DateTime(2023, 10, 23, 10, 0)
      timeSelection.getFirstDayOfWeek should be(expectedMonday)
    }

    "getDaySpan should return a list of consecutive days" in new Fixture {
      val daySpan = timeSelection.getDaySpan
      daySpan.size should be(3)
      daySpan.head should be(fixedDate)
      daySpan(1) should be(fixedDate.plusDays(1))
      daySpan(2) should be(fixedDate.plusDays(2))
    }

    "toString should format the start and end dates correctly" in new Fixture {
      val expectedString = "26/10/2023 - 28/10/2023" // dayCount is 3, so day + 2 days
      timeSelection.toString("dd/MM/yyyy", "dd/MM/yyyy", " - ") should be(expectedString)
    }

    "'+' (plusPeriod) operator should return a new TimeSelection moved forward in time" in new Fixture {
      val newTimeSelection = timeSelection + 1.week.toPeriod
      newTimeSelection.day should be(fixedDate.plusWeeks(1))
      newTimeSelection.dayCount should be(timeSelection.dayCount) // Other fields should be unchanged
    }

    "'-' (minusPeriod) operator should return a new TimeSelection moved backward in time" in new Fixture {
      val newTimeSelection = timeSelection - 2.days.toPeriod
      newTimeSelection.day should be(fixedDate.minusDays(2))
      newTimeSelection.timeFrame should be(timeSelection.timeFrame) // Other fields should be unchanged
    }

    "wholeWeek should return a new TimeSelection for the full week starting Monday" in new Fixture {
      val weekSelection = timeSelection.wholeWeek
      weekSelection.day should be(timeSelection.getFirstDayOfWeek)
      weekSelection.dayCount should be(7)
      weekSelection.timeFrame should be(timeSelection.timeFrame)
    }

    "goToDate should return a new TimeSelection with the specified date" in new Fixture {
      val newDate = new DateTime(2024, 1, 1, 10, 0)
      val newTimeSelection = timeSelection.goToDate(newDate)
      newTimeSelection.day should be(newDate)
      newTimeSelection.dayCount should be(timeSelection.dayCount)
    }

    "startingToday should return a new TimeSelection for today's date but keep the time" in new Fixture {
      val todaySelection = timeSelection.startingToday

      // We can't test for an exact DateTime, but we can test its properties
      todaySelection.day.toLocalDate should be(DateTime.now().toLocalDate)
      todaySelection.day.toLocalTime should be(timeSelection.day.toLocalTime)
      todaySelection.dayCount should be(timeSelection.dayCount)
    }

    "addDayCount" should {
      "return a new TimeSelection when the new count is within bounds" in new Fixture {
        val result = timeSelection.addDayCount(4) // 3 + 4 = 7, which is valid
        result should be(Some(timeSelection.copy(dayCount = 7)))
      }

      "return None when the new count is too high" in new Fixture {
        val result = timeSelection.addDayCount(20) // 3 + 20 = 23, which is > 14
        result should be(None)
      }

      "return None when the new count is too low" in new Fixture {
        val result = timeSelection.addDayCount(-5) // 3 - 5 = -2, which is < 1
        result should be(None)
      }
    }

    "withTimeFrame" should {
      "return a new TimeSelection when the new timeframe is within bounds" in new Fixture {
        val newTimeFrame = 12.hours.toPeriod
        val result = timeSelection.withTimeFrame(newTimeFrame)
        result should be(Some(timeSelection.copy(timeFrame = newTimeFrame)))
      }

//      "return None when the new timeframe is too large" in new Fixture {
//        val newTimeFrame = 25.hours.toPeriod
//        val result = timeSelection.withTimeFrame(newTimeFrame)
//        result should be(None)
//      }
//
//      "return None when the new timeframe is too small" in new Fixture {
//        val newTimeFrame = 30.minutes.toPeriod
//        val result = timeSelection.withTimeFrame(newTimeFrame)
//        result should be(None)
//      }
    }
  }

  "The TimeSelection companion object" should {
    "provide a default instance with correct properties" in {
      val default = TimeSelection.defaultTimeSelection
      default.day.toLocalTime should be(TimeSelection.START_OF_DAY)
      default.dayCount should be(7)
      // Assert against a Period
      default.timeFrame should be(14.hours.toPeriod)
    }
  }

  "A given Conversion" should {
    "allow a TimeSelection to be used as an Interval" in new Fixture {
      // This function explicitly requires an Interval
      def getIntervalDuration(interval: Interval): Period = interval.toPeriod

      // We can pass our TimeSelection directly where an Interval is expected
      val duration = getIntervalDuration(timeSelection)

      duration should be(timeSelection.timeFrameInterval.toPeriod)
    }

    "allow a TimeSelection to be used as a List[DateTime]" in new Fixture {
      // This function explicitly requires a List[DateTime]
      def getListSize(list: List[DateTime]): Int = list.size

      // We can pass our TimeSelection directly where a List is expected
      val size = getListSize(timeSelection)

      size should be(timeSelection.getDaySpan.size)
    }
  }
}