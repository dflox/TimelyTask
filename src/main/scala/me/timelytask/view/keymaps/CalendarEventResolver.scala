package me.timelytask.view.keymaps

import me.timelytask.model.settings.{CALENDAR, EventTypeId}
import me.timelytask.view.eventHandlers.CalendarEventContainer
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
      case EventTypeId("Undo") => eventContainer.undo()
      case EventTypeId("Redo") => eventContainer.redo()
      case EventTypeId("AddRandomTask") => eventContainer.addRandomTask()
      case EventTypeId("Exit") => eventContainer.shutdown()
//      case EventTypeId("GoToToday") => Some(eventContainer.goToToday.call(()))
//      case EventTypeId("GoToDate") => Some(eventContainer.goToDate.call(()))
//      case EventTypeId("ShowWholeWeek") => Some(eventContainer.showWholeWeek.call(()))
//      case EventTypeId("ShowMoreDays") => Some(eventContainer.showMoreDays.call(()))
      case _ => false
    }
  }
}
