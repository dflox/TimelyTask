package me.timelytask.view.views

import me.timelytask.util.Publisher
import me.timelytask.view.events.{Event, GoToDate, GoToToday, NextDay, NextWeek, PreviousDay, PreviousWeek, ShowLessDays, ShowMoreDays, ShowWholeWeek}
import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}

trait CalendarView extends View{
  override def update(calendarViewModel: CalendarViewModel): Boolean
  val nextDay: NextDay
  val previousDay: PreviousDay
  val nextWeek: NextWeek
  val previousWeek: PreviousWeek
  val goToToday: GoToToday 
  val goToDate: GoToDate
  val showWholeWeek: ShowWholeWeek
  val showMoreDays: ShowMoreDays
  val showLessDays: ShowLessDays
}
