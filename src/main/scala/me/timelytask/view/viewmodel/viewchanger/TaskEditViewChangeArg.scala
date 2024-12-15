package me.timelytask.view.viewmodel.viewchanger

import me.timelytask.model.settings.TASKEdit
import me.timelytask.model.Task
import me.timelytask.view.viewmodel.TaskEditViewModel

case class TaskEditViewChangeArg(task: Option[Task]) 
  extends ViewChangeArgument[TASKEdit, TaskEditViewModel] {
  override def apply(viewModel: Option[TaskEditViewModel]): Option[TaskEditViewModel] = {
    if viewModel.isEmpty | task.isEmpty then None
    else Some(viewModel.get.copy(task = task.get))
  }
}
