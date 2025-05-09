package me.timelytask.view.keymaps

import me.timelytask.model.settings.ViewType
import me.timelytask.model.utility.*
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.views.CalendarView

class CalendarKeymap(calendarView: CalendarView[?]) {
  def handleKey(key: Key): Boolean = {
    key match {
      case ShiftRight => calendarView.nextDay.call(())
      case ShiftLeft => calendarView.previousDay.call(())
      case CtrlRight => calendarView.nextWeek.call(())
      case CtrlLeft => calendarView.previousWeek.call(())
      case T => calendarView.goToToday.call(())
      case W => calendarView.showWholeWeek.call(())
      case CtrlPlus => calendarView.showMoreDays.call(())
      case CtrlMinus => calendarView.showLessDays.call(())
      case _ => false
    }
  }
}
