package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.{AddTask, EditTask, UndoManager}
import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.model.utility.InputError
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.view.events.{MoveFocus, SaveTask}
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.viewmodel.dialogmodel.{OptionDialogModel, InputDialogModel}
import me.timelytask.view.viewmodel.elemts.{FocusDirection, TaskCollection, InputField}

class TaskEditEventHandler(using taskEditViewModelPublisher: Publisher[TaskEditViewModel],
                           modelPublisher: Publisher[Model],
                           undoManager: UndoManager,
                           activeViewPublisher: Publisher[ViewType])
  extends EventHandler[TASKEdit, TaskEditViewModel] {

  val viewModel: () => TaskEditViewModel = () => taskEditViewModelPublisher.getValue

  SaveTask.setHandler((taskEditViewModel: TaskEditViewModel) => {
    undoManager.doStep(if taskEditViewModel.isNewTask then
                         AddTask.createCommand(taskEditViewModel.task)
                       else
                         EditTask.createCommand(taskEditViewModel.task))
    activeViewPublisher.update(taskEditViewModel.lastView)
    true
  }, (taskEditViewModel: TaskEditViewModel) => {
    taskEditViewModel.task.isValid match {
      case None =>
        if taskEditViewModel.isNewTask ^ !modelPublisher.getValue.tasks.exists(
          _.uuid == taskEditViewModel.task.uuid) then
          None
        else
          Some(InputError("Task with this UUID already exists. It seems somebody " +
            "else already added this task."))
      case Some(msg) => Some(InputError("Task is not valid: " + msg))
    }
  })

  // Focus Events
  MoveFocus.setHandler((args: FocusDirection) => {
    viewModel().getFocusElementGrid match
      case Some(oldFocusElementGrid) => Some(viewModel().copy(focusElementGrid = oldFocusElementGrid
        .moveFocus(args)))
      case None => None
  }, (args: FocusDirection) => if viewModel().getFocusElementGrid.isEmpty then
                                 Some(InputError("Fatal Error: No focus element grid defined!"))
                               else
                                 None)
} 
