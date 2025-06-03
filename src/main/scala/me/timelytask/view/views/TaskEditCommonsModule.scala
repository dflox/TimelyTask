package me.timelytask.view.views

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.model.Model
import me.timelytask.model.settings.{KeymapConfig, TASKEdit}
import me.timelytask.view.eventHandlers.{TaskEditEventContainer, TaskEditEventContainerImpl}
import me.timelytask.view.keymaps.{EventResolver, TaskEditEventResolver}
import me.timelytask.view.viewmodel.TaskEditViewModel

trait TaskEditCommonsModule extends ViewTypeCommonsModule[TASKEdit, TaskEditViewModel] {
  override lazy val eventContainer: TaskEditEventContainer = wireWith[TaskEditEventContainerImpl](
    () => TaskEditEventContainerImpl(viewModelPublisher, activeViewPublisher, eventHandler,
      coreModule))
  
  override lazy val eventResolver: EventResolver[TASKEdit, TaskEditViewModel] =
    wire[TaskEditEventResolver]

  override def mapViewKeymapConfig(listener: KeymapConfig => Unit): Option[Model] => Unit =
    model => model.map(m => listener(m.config.keymaps(TASKEdit)))
}
