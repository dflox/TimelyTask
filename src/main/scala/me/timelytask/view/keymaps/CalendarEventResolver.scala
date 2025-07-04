package me.timelytask.view.keymaps

import me.timelytask.model.settings.{CALENDAR, EventTypeId}
import me.timelytask.view.events.container.CalendarEventContainer
import me.timelytask.view.viewmodel.CalendarViewModel

class CalendarEventResolver(override protected val eventContainer: CalendarEventContainer) 
  extends EventResolver[CALENDAR, CalendarViewModel] {
  //TODO: add other event mappings
  override def resolveAndCallEvent(eventType: EventTypeId)
  : Boolean = {
    given Conversion[Unit, Boolean]{def apply(unit: Unit): Boolean = true}
    eventType match {
      case EventTypeId("NextDay") => eventContainer.nextDay()
      case EventTypeId("PreviousDay") => eventContainer.previousDay()
      case EventTypeId("NextWeek") => eventContainer.nextWeek()
      case EventTypeId("PreviousWeek") => eventContainer.previousWeek()
      case EventTypeId("GoToToday") => eventContainer.goToToday()
//      case EventTypeId("GoToDate") => Some(eventContainer.goToDate.call(()))
//      case EventTypeId("ShowWholeWeek") => Some(eventContainer.showWholeWeek.call(()))
//      case EventTypeId("ShowMoreDays") => Some(eventContainer.showMoreDays.call(()))
      case _ => false
    }
  }
}
