package me.timelytask.view.eventHandlers

import me.timelytask.model.settings.CALENDAR
import me.timelytask.view.events.Event
import me.timelytask.view.viewmodel.CalendarViewModel

trait CalendarEventContainer extends EventContainer[CALENDAR, CalendarViewModel] {
  def nextDay(): Unit

  def previousDay(): Unit

  def nextWeek(): Unit

  def previousWeek(): Unit
}
