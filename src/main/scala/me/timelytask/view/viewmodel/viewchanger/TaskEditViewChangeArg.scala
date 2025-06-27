package me.timelytask.view.viewmodel.viewchanger

import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.model.task.Task
import me.timelytask.view.viewmodel.TaskEditViewModel

case class TaskEditViewChangeArg(task: Option[Task], callingView: Option[ViewType] = None)
  extends ViewChangeArgument[TASKEdit, TaskEditViewModel] {
  override def apply[VMType >: TaskEditViewModel](viewModel: Option[VMType])
  : Option[VMType] = {
    if viewModel.isEmpty | task.isEmpty then None
    else viewModel match
      case Some(value: TaskEditViewModel) => Some(value.copy(task = task.get, lastView =
        callingView))
      case None => None
      case Some(_) => None
  }
}
