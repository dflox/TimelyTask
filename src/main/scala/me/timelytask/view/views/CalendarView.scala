package me.timelytask.view.views

import me.timelytask.util.Publisher
import me.timelytask.view.events.{Event, GoToDate, GoToToday, NextDay, NextWeek, PreviousDay, PreviousWeek, ShowLessDays, ShowMoreDays, ShowWholeWeek}
import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}

trait CalendarView extends View{
  override def update(calendarViewModel: CalendarViewModel): Boolean
  val nextDay: NextDay = NextDay.createEvent()
  val previousDay: PreviousDay = PreviousDay.createEvent()
  val nextWeek: NextWeek = NextWeek.createEvent()
  val previousWeek: PreviousWeek = PreviousWeek.createEvent()
  val goToToday: GoToToday = GoToToday.createEvent()
  val goToDate: GoToDate = GoToDate.createEvent()
  val showWholeWeek: ShowWholeWeek = ShowWholeWeek.createEvent()
  val showMoreDays: ShowMoreDays = ShowMoreDays.createEvent()
  val showLessDays: ShowLessDays = ShowLessDays.createEvent()
}
