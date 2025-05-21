package me.timelytask.view.views

import com.softwaremill.macwire.wire
import me.timelytask.model.settings.CALENDAR
import me.timelytask.view.eventHandlers.{EventContainer, TaskEditEventContainer, TaskEditEventContainerImpl}
import me.timelytask.view.events.*
import me.timelytask.view.viewmodel.CalendarViewModel

trait CalendarView[RenderType] extends View[CALENDAR, CalendarViewModel, RenderType] {
}
