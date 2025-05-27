package me.timelytask.view.views

import me.timelytask.model.settings.CALENDAR
import me.timelytask.view.viewmodel.CalendarViewModel

trait CalendarView[RenderType] extends View[CALENDAR, CalendarViewModel, RenderType] {
}
