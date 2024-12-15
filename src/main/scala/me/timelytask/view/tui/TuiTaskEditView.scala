package me.timelytask.view.tui

import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.views.TaskEditView

class TuiTaskEditView(override val render: (String, ViewType) => Unit, tuiModel: Unit => ModelTUI)
                     (using override val keymapPublisher: Publisher[Keymap[TASKEdit,
  TaskEditViewModel, TaskEditView[?]]],
                      val viewModelPublisher: Publisher[TaskEditViewModel]) extends TaskEditView[String] {
  
  override def update(viewModel: TaskEditViewModel): Boolean = {
    render(TaskEditViewStringFactory.buildString(viewModel, tuiModel(())), TASKEdit)
    true
  }
}
