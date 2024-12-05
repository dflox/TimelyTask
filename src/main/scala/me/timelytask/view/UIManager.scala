package me.timelytask.view

import me.timelytask.view.events.NextDay
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.views.CalendarView

trait UIManager {
  val calendarView: CalendarView
  
}
