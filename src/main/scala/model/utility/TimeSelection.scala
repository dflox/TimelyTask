package model.utility

import com.github.nscala_time.time.Imports.*

case class TimeSelection(day: DateTime, dayCount: Int, timeFrame: Period){
  val timeFrameInterval: Interval = new Interval(day, day.withPeriodAdded(timeFrame, 1)) 
  // Get the first Day of the week
  def getFirstDayOfWeek: DateTime = {
    day - (day.getDayOfWeek - 1).days
  }

  // Get a list of days starting with a given day and going forward for a given TimePeriod
  def getDaySpan: List[DateTime] = {
    (0 until dayCount).map(day + _.days).toList
  }

  // Get a time period in a given Format as String starting with a given day and going forward for a given TimePeriod
  def toString(formatStart: String, formatEnd: String, separator: String): String = {
    val lastDay: DateTime = day + (dayCount - 1).days
    day.toString(formatStart) + separator + lastDay.toString(formatEnd)
  }
}

object TimeSelection {
  val defaultTimeSelection: TimeSelection = TimeSelection(DateTime.now(), 7, 14.hours)
}
