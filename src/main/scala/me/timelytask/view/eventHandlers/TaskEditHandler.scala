package me.timelytask.view.eventHandlers

import me.timelytask.util.Publisher
import me.timelytask.view.events.SaveTask
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.model.Task

class TaskEditHandler(using taskEditViewModelPublisher: Publisher[TaskEditViewModel]) 
  extends EventHandler[TaskEditViewModel] {

  val viewModel: () => TaskEditViewModel = () => taskEditViewModelPublisher.getValue
  
  SaveTask.setHandler((task: Task) => {
    // TODO: Call Command that saves the task
  })
} 
