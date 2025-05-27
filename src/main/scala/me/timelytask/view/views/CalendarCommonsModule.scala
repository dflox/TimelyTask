package me.timelytask.view.views

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.model.Model
import me.timelytask.model.settings.{CALENDAR, KeymapConfig}
import me.timelytask.view.eventHandlers.{CalendarEventContainer, CalendarEventContainerImpl}
import me.timelytask.view.keymaps.{CalendarEventResolver, EventResolver}
import me.timelytask.view.viewmodel.CalendarViewModel

trait CalendarCommonsModule extends ViewTypeCommonsModule[CALENDAR, CalendarViewModel] {
  override lazy val eventContainer: CalendarEventContainer = wireWith[CalendarEventContainerImpl](
    () => CalendarEventContainerImpl(viewModelPublisher, activeViewPublisher, eventHandler,
      coreModule))
  
  override lazy val eventResolver: EventResolver[CALENDAR, CalendarViewModel] =
    wire[CalendarEventResolver]

  override def mapKeymapConfig(listener: KeymapConfig => Unit): Option[Model] => Unit =
    model => model.map(m => listener(m.config.keymaps(CALENDAR)))
}
