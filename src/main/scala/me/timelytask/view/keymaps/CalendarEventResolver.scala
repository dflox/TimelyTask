package me.timelytask.view.keymaps

import me.timelytask.model.settings.{CALENDAR, EventTypeId}
import me.timelytask.view.eventHandlers.{CalendarEventContainer, EventContainer}
import me.timelytask.view.events.Event
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.{CalendarView, View}

class CalendarEventResolver(override protected val eventContainer: CalendarEventContainer) 
  extends EventResolver[CALENDAR, CalendarViewModel] {
  //TODO: add other event mappings
  override def resolveAndCallEvent(eventType: EventTypeId)
  : Option[Boolean] = {
    eventType match {
      case EventTypeId("NextDay") => Some(eventContainer.NextDay.call(()))
      case EventTypeId("PreviousDay") => Some(eventContainer.PreviousDay.call(()))
      case EventTypeId("NextWeek") => Some(eventContainer.NextWeek.call(()))
      case EventTypeId("PreviousWeek") => Some(eventContainer.PreviousWeek.call(()))
//      case EventTypeId("GoToToday") => Some(eventContainer.goToToday.call(()))
//      case EventTypeId("GoToDate") => Some(eventContainer.goToDate.call(()))
//      case EventTypeId("ShowWholeWeek") => Some(eventContainer.showWholeWeek.call(()))
//      case EventTypeId("ShowMoreDays") => Some(eventContainer.showMoreDays.call(()))
      case _ => None
    }
  }
}
