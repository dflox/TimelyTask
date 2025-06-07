package me.timelytask.view.views.commonsModules.commonsModuleImpl

import me.timelytask.core.CoreModule
import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.events.EventHandler
import me.timelytask.view.events.container.GlobalEventContainer
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.commonsModules.{CalendarCommonsModule, ViewTypeCommonsModule}

class CalendarCommonsModuleImpl(coreModule: CoreModule,
                                activeViewPublisher: Publisher[ViewType],
                                eventHandler: EventHandler,
                                globalEventContainer: GlobalEventContainer)
  extends CalendarCommonsModule
  with ViewTypeCommonsModule[CALENDAR, CalendarViewModel]
    (coreModule, activeViewPublisher, eventHandler, globalEventContainer)
