package me.timelytask.view.views

import me.timelytask.core.CoreModule
import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.eventHandlers.EventHandler
import me.timelytask.view.viewmodel.CalendarViewModel

class CalendarCommonsModuleImpl(coreModule: CoreModule,
                                activeViewPublisher: Publisher[ViewType],
                                eventHandler: EventHandler)
  extends CalendarCommonsModule
  with ViewTypeCommonsModule[CALENDAR, CalendarViewModel]
    (coreModule, activeViewPublisher, eventHandler)
