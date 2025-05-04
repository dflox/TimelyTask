package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.{AddTask, Command, CommandHandler, EditTask}
import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.model.utility.InputError
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.view.events.{CancelTask, MoveFocus, SaveTask}
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.viewmodel.elemts.FocusDirection

import java.util.concurrent.LinkedBlockingQueue
import scala.util.{Failure, Success, Try}

class TaskEditEventHandler(taskEditViewModelPublisher: Publisher[TaskEditViewModel],
                           modelPublisher: Publisher[Model],
                           undoManager: CommandHandler,
                           activeViewPublisher: Publisher[ViewType],
                           commandQueue: LinkedBlockingQueue[Command[?]])
  extends EventHandler[TASKEdit, TaskEditViewModel](
    taskEditViewModelPublisher, modelPublisher, undoManager, activeViewPublisher, commandQueue) {

  val viewModel: () => Option[TaskEditViewModel] = () => taskEditViewModelPublisher.getValue

  override def init(): Unit = {
    SaveTask.setHandler((taskEditViewModel: TaskEditViewModel) => {
      commandQueue.put(if taskEditViewModel.isNewTask then
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

    // TODO: Implement CancelTask event

    CancelTask.setHandler((taskEditViewModel: TaskEditViewModel) => false, 
      (taskEditViewModel: TaskEditViewModel) => Some(InputError
      ("Unsupported Event: CancelTask")))
    
    modelPublisher.addListener(modelUpdate)


    //  ChangeView.addHandler({
    //    case viewChangeArg: ViewChangeArgument[TASKEdit, TaskEditViewModel] =>
    //      taskEditViewModelPublisher.update(viewChangeArg(taskEditViewModelPublisher.getValue))
    //      activeViewPublisher.update(Some(TASKEdit))
    //      true
    //    case _ => false
    //  }, (args: ViewChangeArgumentWrapper[ViewType, ?, ?]) => args.arg match
    //    case arg: TaskEditViewChangeArg => None
    //    case _ => Some(InputError("Cannot change view to calendar view."))
    //  )

    // Focus Events
    MoveFocus.addHandler[TASKEdit]((args: FocusDirection) => {
      Try[TaskEditViewModel] {
        viewModel().get.copy(focusElementGrid = viewModel().get.getFocusElementGrid.get.moveFocus
          (args))
      } match
        case Success(value) => Some(value)
        case Failure(exception) => None
    },
      (args: FocusDirection) => if viewModel().isEmpty | viewModel().get.getFocusElementGrid.isEmpty
                                then
                                  Some(InputError("Fatal Error: No focus element grid defined!"))
                                else
                                  None)
  }

  private def modelUpdate(model: Option[Model]): Boolean = {
    if model.isEmpty then return false
    val task = model.get.tasks.find(_.uuid == viewModel().get.task.uuid)
    if task.isEmpty then return false
    if task.get == viewModel().get.task then return false
    Some(viewModel().get.copy(task = task.get))
  }
} 
