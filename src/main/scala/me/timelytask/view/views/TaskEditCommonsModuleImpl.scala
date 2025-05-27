package me.timelytask.view.views

import me.timelytask.core.CoreModule
import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.eventHandlers.EventHandler
import me.timelytask.view.viewmodel.TaskEditViewModel

class TaskEditCommonsModuleImpl(coreModule: CoreModule,
                                activeViewPublisher: Publisher[ViewType],
                                eventHandler: EventHandler)
  extends TaskEditCommonsModule
  with ViewTypeCommonsModule[TASKEdit, TaskEditViewModel]
    (coreModule, activeViewPublisher, eventHandler)
