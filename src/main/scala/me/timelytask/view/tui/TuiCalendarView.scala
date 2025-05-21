package me.timelytask.view.tui

import com.softwaremill.macwire.wire
import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.eventHandlers.{CalendarEventContainerImpl, EventContainer}
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.viewmodel.dialogmodel.OptionDialogModel
import me.timelytask.view.views.*

class TuiCalendarView(override val render: (String, ViewType) => Unit,
                      private val tuiModel: Unit => ModelTUI,
                      override val dialogFactory: DialogFactory[String],
                      override val viewTypeCommonsModule: CalendarCommonsModule)
  extends CalendarView[String] 
  with View[CALENDAR, CalendarViewModel, String](viewTypeCommonsModule) {
  
  override def update(viewModel: Option[CalendarViewModel]): Boolean = {
    if viewModel.isEmpty then return false
    currentlyRendered = Some(CalendarViewStringFactory.buildString(viewModel.get, tuiModel(())))
    render(currentlyRendered.get, CALENDAR)
    true
  }
}
