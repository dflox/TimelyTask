package me.timelytask.model.utility

import com.github.nscala_time.time.Imports.*

import scala.annotation.targetName

case class TimeSelection(day: DateTime, dayCount: Int, timeFrame: Period) {
  val MIN_DAY_COUNT: Int = 1
  val MAX_DAY_COUNT: Int = 14
  val MIN_TIME_FRAME: Period = 1.hour
  val MAX_TIME_FRAME: Period = 24.hours

  val timeFrameInterval: Interval = new Interval(day, day.withPeriodAdded(timeFrame, 1))

  // Get the first Day of the week
  def getFirstDayOfWeek: DateTime = {
    day - (day.getDayOfWeek - 1).days
  }

  // Get a list of days starting with a given day and going forward for a given TimePeriod
  def getDaySpan: List[DateTime] = {
    (0 until dayCount).map(day + _.days).toList
  }

  // Get a time period in a given Format as String starting with a given day and going forward 
  // for a given TimePeriod
  def toString(formatStart: String, formatEnd: String, separator: String): String = {
    val lastDay: DateTime = day + (dayCount - 1).days
    day.toString(formatStart) + separator + lastDay.toString(formatEnd)
  }

  @targetName("plusPeriod")
  def +(period: Period): TimeSelection = {
    TimeSelection(day + period, dayCount, timeFrame)
  }

  @targetName("minusPeriod")
  def -(period: Period): TimeSelection = {
    TimeSelection(day - period, dayCount, timeFrame)
  }

  def wholeWeek: TimeSelection = {
    TimeSelection(getFirstDayOfWeek, 7, timeFrame)
  }

  def addDayCount(days: Int): Option[TimeSelection] = {
    if (dayCount + days > MAX_DAY_COUNT | dayCount + days < MIN_DAY_COUNT) {
      None
    } else {
      Some(TimeSelection(day, dayCount + days, timeFrame))
    }
  }

  def goToDate(date: DateTime): TimeSelection = {
    TimeSelection(date, dayCount, timeFrame)
  }

  def startingToday: TimeSelection = {
    TimeSelection(DateTime.now().withTime(day.toLocalTime), dayCount, timeFrame)
  }

  def withTimeFrame(timeFrame: Period): Option[TimeSelection] = {
    if ((MIN_TIME_FRAME - timeFrame).millis > 0 || (MAX_TIME_FRAME - timeFrame).millis < 0) {
      None
    } else {
      Some(TimeSelection(day, dayCount, timeFrame))
    }
  }
}

given Conversion[TimeSelection, Interval] with {
  def apply(timeSelection: TimeSelection): Interval = timeSelection.timeFrameInterval
}

given Conversion[TimeSelection, List[DateTime]] with {
  def apply(timeSelection: TimeSelection): List[DateTime] = timeSelection.getDaySpan
}

object TimeSelection {
  val START_OF_DAY: LocalTime = new LocalTime(6, 0, 0)
  val defaultTimeSelection: TimeSelection = TimeSelection(DateTime.now().withTime(START_OF_DAY),
    7, 14.hours)
}
