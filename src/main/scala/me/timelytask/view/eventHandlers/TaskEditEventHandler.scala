package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.{AddTask, EditTask, UndoManager}
import me.timelytask.util.Publisher
import me.timelytask.view.events.SaveTask
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.model.{Model, Task}

class TaskEditEventHandler(using taskEditViewModelPublisher: Publisher[TaskEditViewModel],
                           modelPublisher: Publisher[Model],
                           undoManager: UndoManager) 
  extends EventHandler[TaskEditViewModel] {

  val viewModel: () => TaskEditViewModel = () => taskEditViewModelPublisher.getValue
  
  SaveTask.setHandler((task: Task) => {
    if model().tasks.contains(task) then
      undoManager.doStep(EditTask.createCommand(task))
    else
      undoManager.doStep(AddTask.createCommand(task))
    }
  )
} 
