package me.timelytask.view.views.commonsModules.commonsModuleImpl

import me.timelytask.core.CoreModule
import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.events.EventHandler
import me.timelytask.view.events.container.GlobalEventContainer
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.views.commonsModules.{TaskEditCommonsModule, ViewTypeCommonsModule}

class TaskEditCommonsModuleImpl(coreModule: CoreModule,
                                activeViewPublisher: Publisher[ViewType],
                                eventHandler: EventHandler,
                                globalEventContainer: GlobalEventContainer)
  extends TaskEditCommonsModule
  with ViewTypeCommonsModule[TASKEdit, TaskEditViewModel]
    (coreModule, activeViewPublisher, eventHandler, globalEventContainer)
