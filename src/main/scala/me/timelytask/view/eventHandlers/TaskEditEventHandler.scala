package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.{AddTask, EditTask, UndoManager}
import me.timelytask.model.settings.{TASKEdit, ViewType}
import me.timelytask.model.utility.InputError
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.view.events.SaveTask
import me.timelytask.view.viewmodel.TaskEditViewModel

class TaskEditEventHandler(using taskEditViewModelPublisher: Publisher[TaskEditViewModel],
                           modelPublisher: Publisher[Model],
                           undoManager: UndoManager,
                           activeViewPublisher: Publisher[ViewType])
  extends EventHandler[TASKEdit, TaskEditViewModel] {

  val viewModel: () => TaskEditViewModel = () => taskEditViewModelPublisher.getValue

  SaveTask.setHandler((taskEditViewModel: TaskEditViewModel) => {
    val task = taskEditViewModel.taskBuilder.build()
    undoManager.doStep(if taskEditViewModel.isNewTask then
                         AddTask.createCommand(task)
                       else
                         EditTask.createCommand(task))
    activeViewPublisher.update(taskEditViewModel.lastView)
    true
  }, (taskEditViewModel: TaskEditViewModel) => {
    val task = taskEditViewModel.taskBuilder.build()
    task.isValid match {
      case None =>
        if taskEditViewModel.isNewTask ^ !modelPublisher.getValue.tasks.exists(
          _.uuid == task.uuid) then
          None
        else
          Some(InputError("Task with this UUID already exists. It seems somebody " +
            "else already added this task."))
      case Some(msg) => Some(InputError("Task is not valid: " + msg))
    }
  })

} 
