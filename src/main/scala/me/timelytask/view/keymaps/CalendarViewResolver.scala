package me.timelytask.view.keymaps

import me.timelytask.model.settings.{CALENDAR, EventTypeId}
import me.timelytask.view.events.Event
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.CalendarView

class CalendarViewResolver extends EventResolver[CALENDAR, CalendarViewModel, CalendarView[?]] {
  override def resolveEvent[Args](eventType: EventTypeId, view: CalendarView[?])
  : Option[Event[Args]] = {
    // Type-safe casting helper
    def castIfPossible[T](value: Any): Option[T] =
      value match
        case t: T => Some(t)
        case _ => None

    eventType match {
      case EventTypeId("NextDay") => castIfPossible[Event[Args]](view.nextDay)
      case EventTypeId("PreviousDay") => castIfPossible[Event[Args]](view.previousDay)
      case EventTypeId("NextWeek") => castIfPossible[Event[Args]](view.nextWeek)
      case EventTypeId("PreviousWeek") => castIfPossible[Event[Args]](view.previousWeek)
      case EventTypeId("GoToToday") => castIfPossible[Event[Args]](view.goToToday)
      case EventTypeId("GoToDate") => castIfPossible[Event[Args]](view.goToDate)
      case EventTypeId("ShowWholeWeek") => castIfPossible[Event[Args]](view.showWholeWeek)
      case EventTypeId("ShowMoreDays") => castIfPossible[Event[Args]](view.showMoreDays)
      case _ => None
    }
  }
}
