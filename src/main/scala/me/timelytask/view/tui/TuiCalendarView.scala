package me.timelytask.view.tui

import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.viewmodel.dialogmodel.OptionDialogModel
import me.timelytask.view.views.{CalendarView, DialogFactory, View}

class TuiCalendarView(override val render: (String, ViewType) => Unit, tuiModel: Unit => ModelTUI)
                     (using override val keymapPublisher: Publisher[Keymap[CALENDAR, 
  CalendarViewModel, View[CALENDAR, CalendarViewModel, ?]]],
                      val viewModelPublisher: Publisher[CalendarViewModel],
                      override val dialogFactory: DialogFactory[String])
extends CalendarView[String] {
  
  override def update(viewModel: Option[CalendarViewModel]): Boolean = {
    if viewModel.isEmpty then return false
    currentlyRendered = Some(CalendarViewStringFactory.buildString(viewModel.get, tuiModel(())))
    render(currentlyRendered.get, CALENDAR)
    true
  }
  
}
