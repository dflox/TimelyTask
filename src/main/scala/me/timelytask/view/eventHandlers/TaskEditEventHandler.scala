package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.{AddTask, EditTask, UndoManager}
import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.model.utility.InputError
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.view.events.argwrapper.ViewChangeArgumentWrapper
import me.timelytask.view.events.{ChangeView, MoveFocus, SaveTask}
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.viewmodel.dialogmodel.{InputDialogModel, OptionDialogModel}
import me.timelytask.view.viewmodel.elemts.{FocusDirection, InputField, TaskCollection}
import me.timelytask.view.viewmodel.viewchanger.{TaskEditViewChangeArg, ViewChangeArgument}

import scala.util.{Failure, Success, Try}

class TaskEditEventHandler(using taskEditViewModelPublisher: Publisher[TaskEditViewModel],
                           modelPublisher: Publisher[Model],
                           undoManager: UndoManager,
                           activeViewPublisher: Publisher[ViewType])
  extends EventHandler[TASKEdit, TaskEditViewModel] {

  val viewModel: () => Option[TaskEditViewModel] = () => taskEditViewModelPublisher.getValue

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
        if taskEditViewModel.isNewTask ^ !(modelPublisher.getValue match {
          case Some(model) =>
            model.tasks.exists(
              _.uuid == taskEditViewModel.task.uuid)
          case None => false
        }) then
          None
        else
          Some(InputError("Task with this UUID already exists. It seems somebody " +
            "else already added this task."))
      case Some(msg) => Some(InputError("Task is not valid: " + msg))
    }
  })

  ChangeView.addHandler({
    case viewChangeArg: ViewChangeArgument[TASKEdit, TaskEditViewModel] =>
      taskEditViewModelPublisher.update(viewChangeArg(taskEditViewModelPublisher.getValue))
      activeViewPublisher.update(Some(TASKEdit))
      true
    case _ => false
  }, (args: ViewChangeArgumentWrapper[ViewType]) => args.arg match
    case arg: TaskEditViewChangeArg => None
    case _ => Some(InputError("Cannot change view to calendar view."))
  )
  
  // Focus Events
  MoveFocus.addHandler[TASKEdit]((args: FocusDirection) => {
    Try[TaskEditViewModel] {
      viewModel().get.copy(focusElementGrid = viewModel().get.getFocusElementGrid.get.moveFocus
        (args))
    } match
      case Success(value) => Some(value)
      case Failure(exception) => None
  }, (args: FocusDirection) => if viewModel().isEmpty | viewModel().get.getFocusElementGrid.isEmpty
                               then
                                 Some(InputError("Fatal Error: No focus element grid defined!"))
                               else
                                 None)
} 
