package me.timelytask.view.views.commonsModules.commonsModuleImpl

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.core.CoreModule
import me.timelytask.model.Model
import me.timelytask.model.settings.{KeymapConfig, TASKEdit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.events.EventHandler
import me.timelytask.view.events.container.contailerImpl.TaskEditEventContainerImpl
import me.timelytask.view.events.container.{GlobalEventContainer, TaskEditEventContainer}
import me.timelytask.view.keymaps.{EventResolver, TaskEditEventResolver}
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.views.commonsModules.{TaskEditCommonsModule, ViewTypeCommonsModule}

class TaskEditCommonsModuleImpl(coreModule: CoreModule,
                                activeViewPublisher: Publisher[ViewType],
                                eventHandler: EventHandler,
                                globalEventContainer: GlobalEventContainer)
  extends TaskEditCommonsModule
  with ViewTypeCommonsModule[TASKEdit, TaskEditViewModel]
    (coreModule, activeViewPublisher, eventHandler, globalEventContainer) {
  override lazy val eventContainer: TaskEditEventContainer = wireWith[TaskEditEventContainerImpl](
    () => TaskEditEventContainerImpl(viewModelPublisher, activeViewPublisher, eventHandler,
      coreModule, globalEventContainer.userToken))

  override lazy val eventResolver: EventResolver[TASKEdit, TaskEditViewModel] =
    wire[TaskEditEventResolver]

  override def mapViewKeymapConfig(listener: KeymapConfig => Unit): Option[Model] => Unit =
    model => model.map(m => listener(m.config.keymaps(TASKEdit)))
}
