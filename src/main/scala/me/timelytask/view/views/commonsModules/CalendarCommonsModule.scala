package me.timelytask.view.views.commonsModules

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.model.Model
import me.timelytask.model.settings.{CALENDAR, KeymapConfig}
import me.timelytask.view.events.container.CalendarEventContainer
import me.timelytask.view.events.container.contailerImpl.CalendarEventContainerImpl
import me.timelytask.view.keymaps.{CalendarEventResolver, EventResolver}
import me.timelytask.view.viewmodel.CalendarViewModel

trait CalendarCommonsModule extends ViewTypeCommonsModule[CALENDAR, CalendarViewModel] {
  override lazy val eventContainer: CalendarEventContainer
  override lazy val eventResolver: EventResolver[CALENDAR, CalendarViewModel]
}