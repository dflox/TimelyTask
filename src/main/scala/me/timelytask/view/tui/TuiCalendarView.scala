package me.timelytask.view.tui

import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.{CalendarView, View}

class TuiCalendarView(override val render: (String, ViewType) => Unit, tuiModel: Unit => ModelTUI)
                     (using override val keymapPublisher: Publisher[Keymap[CALENDAR, 
  CalendarViewModel, CalendarView[?]]],
                      val viewModelPublisher: Publisher[CalendarViewModel])
  extends CalendarView[String] {
  
  override def update(viewModel: CalendarViewModel): Boolean = {
    render(CalendarViewStringFactory.buildString(viewModel, tuiModel(())), CALENDAR)
    true
  }
  
}
