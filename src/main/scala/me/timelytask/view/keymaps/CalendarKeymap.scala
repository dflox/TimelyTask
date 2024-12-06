package me.timelytask.view.keymaps

import me.timelytask.model.settings.ViewType
import me.timelytask.model.utility.*
import me.timelytask.util.Publisher
import me.timelytask.view.views.CalendarView

class CalendarKeymap(calendarView: CalendarView) {
  def handleKey(key: Key): Boolean = {
    key match {
      case ArrowUp => calendarView.nextDay.call(())
    }
  }
}
