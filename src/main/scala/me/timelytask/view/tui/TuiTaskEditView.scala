package me.timelytask.view.tui

import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.viewmodel.dialogmodel.{InputDialogModel, OptionDialogModel}
import me.timelytask.view.views.{DialogFactory, TaskEditView, View}

class TuiTaskEditView(override val render: (String, ViewType) => Unit, tuiModel: Unit => ModelTUI)
                     (using override val keymapPublisher: Publisher[Keymap[TASKEdit,
                       TaskEditViewModel, View[TASKEdit, TaskEditViewModel, ?]]],
                      val viewModelPublisher: Publisher[TaskEditViewModel],
                      override val dialogFactory: DialogFactory[String])
  extends TaskEditView[String] {

  override def update(viewModel: Option[TaskEditViewModel]): Boolean = {
    if viewModel.isEmpty then return false
    currentlyRendered = Some(TaskEditViewStringFactory.buildString(viewModel.get, tuiModel(())))
    render(currentlyRendered.get, TASKEdit)
    true
  }
}
