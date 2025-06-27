package me.timelytask.view.views.commonsModules

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.model.Model
import me.timelytask.model.settings.{KeymapConfig, TASKEdit}
import me.timelytask.view.events.container.TaskEditEventContainer
import me.timelytask.view.events.container.contailerImpl.TaskEditEventContainerImpl
import me.timelytask.view.keymaps.{EventResolver, TaskEditEventResolver}
import me.timelytask.view.viewmodel.TaskEditViewModel

trait TaskEditCommonsModule extends ViewTypeCommonsModule[TASKEdit, TaskEditViewModel] {
  override lazy val eventContainer: TaskEditEventContainer
  override lazy val eventResolver: EventResolver[TASKEdit, TaskEditViewModel]
}