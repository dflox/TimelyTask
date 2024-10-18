package view

import com.github.nscala_time.time.Imports.*

object UtilTUI {
  // write a given amount of dashes
  def createLine(length: Int): String = {
    require(length >= 0, "Length cannot be negative")
    "-" * length
  }

  // write a given amount of spaces
  def createSpace(length: Int): String = {
    require(length >= 0, "Length cannot be negative")
    " " * length
  }

  // Get the first Day of the week
  def getFirstDayOfWeek(day: DateTime): DateTime = {
    day - (day.getDayOfWeek - 1).days
  }

  // Get a list of days starting with a given day and going forward for a given TimePeriod
  def getDaySpan(startDay: DateTime, period: Int): List[DateTime] = {
    (0 until period).map(startDay + _.days).toList
  }

  // Get a time period in a given Format as String starting with a given day and going forward for a given TimePeriod
  def getDatePeriod(day: DateTime, timePeriod: Int, formatStart: String, formatEnd: String, separator: String): String = {
    val lastDay: DateTime = day + (timePeriod - 1).days
    day.toString(formatStart) + separator + lastDay.toString(formatEnd)
  }

  // Align the text to the top by adding newlines
  def alignTop(totalLines: Int, used: Int): String = {
    val unused: Int = totalLines - used
    if (unused > 0) {
      "\n" * unused
    } else {
      ""
    }
  }
}
