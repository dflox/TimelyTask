package me.timelytask.view.views

import me.timelytask.core.CoreModule
import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.eventHandlers.{EventHandler, GlobalEventContainer}
import me.timelytask.view.viewmodel.TaskEditViewModel

class TaskEditCommonsModuleImpl(coreModule: CoreModule,
                                activeViewPublisher: Publisher[ViewType],
                                eventHandler: EventHandler,
                                globalEventContainer: GlobalEventContainer)
  extends TaskEditCommonsModule
  with ViewTypeCommonsModule[TASKEdit, TaskEditViewModel]
    (coreModule, activeViewPublisher, eventHandler, globalEventContainer)
