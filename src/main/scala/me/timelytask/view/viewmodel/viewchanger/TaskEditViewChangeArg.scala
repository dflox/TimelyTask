package me.timelytask.view.viewmodel.viewchanger

import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.model.Task
import me.timelytask.view.viewmodel.{TaskEditViewModel, ViewModel}

case class TaskEditViewChangeArg(task: Option[Task], callingView: Option[ViewType] = None) 
  extends ViewChangeArgument[TASKEdit, ViewModel[TASKEdit]] {
  override def apply(viewModel: Option[TaskEditViewModel]): Option[TaskEditViewModel] = {
    if viewModel.isEmpty | task.isEmpty then None
    else Some(viewModel.get.copy(task = task.get, lastView = callingView))
  }
}
