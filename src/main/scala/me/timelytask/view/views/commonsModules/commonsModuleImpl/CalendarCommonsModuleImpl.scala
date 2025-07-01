package me.timelytask.view.views.commonsModules.commonsModuleImpl

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.core.CoreModule
import me.timelytask.model.Model
import me.timelytask.model.settings.{CALENDAR, KeymapConfig, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.events.EventHandler
import me.timelytask.view.events.container.contailerImpl.CalendarEventContainerImpl
import me.timelytask.view.events.container.{CalendarEventContainer, GlobalEventContainer}
import me.timelytask.view.keymaps.{CalendarEventResolver, EventResolver}
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.commonsModules.{CalendarCommonsModule, ViewTypeCommonsModule}

class CalendarCommonsModuleImpl(coreModule: CoreModule,
                                activeViewPublisher: Publisher[ViewType],
                                eventHandler: EventHandler,
                                globalEventContainer: GlobalEventContainer)
  extends CalendarCommonsModule
  with ViewTypeCommonsModule[CALENDAR, CalendarViewModel]
    (coreModule, activeViewPublisher, eventHandler, globalEventContainer){
  override lazy val eventContainer: CalendarEventContainer = wireWith[CalendarEventContainerImpl](
    () => CalendarEventContainerImpl(viewModelPublisher, activeViewPublisher, eventHandler,
      coreModule, globalEventContainer.userToken))

  override lazy val eventResolver: EventResolver[CALENDAR, CalendarViewModel] =
    wire[CalendarEventResolver]

  override def mapViewKeymapConfig(listener: KeymapConfig => Unit): Option[Model] => Unit =
    model => model.map(m => listener(m.config.keymaps(CALENDAR).addMapping(m.config.globalKeymap)))
}
