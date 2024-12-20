package me.timelytask.view.keymaps

import me.timelytask.model.settings.{CALENDAR, EventTypeId}
import me.timelytask.view.events.Event
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.{CalendarView, View}

class CalendarViewResolver extends EventResolver[CALENDAR, CalendarViewModel, View[CALENDAR,
  CalendarViewModel, ?]] {
  override def resolveAndCallEvent(eventType: EventTypeId, view: View[CALENDAR,
    CalendarViewModel, ?])
  : Option[Boolean] = {
    view match {
      case calendarView: CalendarView[?] => callEvent(eventType, calendarView)
      case _ => None
    }
  }
  
  private def callEvent(eventType: EventTypeId, calendarView: CalendarView[?]): Option[Boolean] = {
    eventType match {
      case EventTypeId("NextDay") => Some(calendarView.nextDay.call(()))
      case EventTypeId("PreviousDay") => Some(calendarView.previousDay.call(()))
      case EventTypeId("NextWeek") => Some(calendarView.nextWeek.call(()))
      case EventTypeId("PreviousWeek") => Some(calendarView.previousWeek.call(()))
      case EventTypeId("GoToToday") => Some(calendarView.goToToday.call(()))
      case EventTypeId("GoToDate") => Some(calendarView.goToDate.call(()))
      case EventTypeId("ShowWholeWeek") => Some(calendarView.showWholeWeek.call(()))
      case EventTypeId("ShowMoreDays") => Some(calendarView.showMoreDays.call(()))
      case _ => None
    }
  }
}
