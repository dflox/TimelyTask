package me.timelytask.view.views.viewImpl.tui

import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.viewmodel.dialogmodel.{InputDialogModel, OptionDialogModel}
import me.timelytask.view.views.*
import me.timelytask.view.views.commonsModules.TaskEditCommonsModule
import me.timelytask.view.views.viewImpl.tui.{ModelTUI, TaskEditViewStringFactory}

class TuiTaskEditView(override val render: (String, ViewType) => Unit,
                      private val tuiModel: Unit => ModelTUI,
                      override val dialogFactory: DialogFactory[String],
                      override val viewTypeCommonsModule: TaskEditCommonsModule)
  extends TaskEditView[String]
  with View[TASKEdit, TaskEditViewModel, String](viewTypeCommonsModule) {

  override def update(viewModel: Option[TaskEditViewModel]): Boolean = {
    if viewModel.isEmpty then return false
    currentlyRendered = Some(TaskEditViewStringFactory.buildString(viewModel.get, tuiModel(())))
    render(currentlyRendered.get, TASKEdit)
    true
  }
}
