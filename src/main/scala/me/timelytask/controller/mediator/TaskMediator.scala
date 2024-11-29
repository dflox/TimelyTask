package me.timelytask.controller.mediator

import me.timelytask.model.Task
import me.timelytask.controller.Controller
import me.timelytask.view.viewmodel.ViewModelStatus

class TaskMediator(controller: Controller, viewModel: ViewModelStatus) extends Mediator {
  override def notify(sender: Any, event: String): Unit = {
    event match {
      case "TaskStarted" =>
        viewModel.updateTaskStatus(sender.asInstanceOf[Task], "Started")
      case "TaskCompleted" =>
        viewModel.updateTaskStatus(sender.asInstanceOf[Task], "Completed")
      case "TaskCancelled" =>
        viewModel.updateTaskStatus(sender.asInstanceOf[Task], "Cancelled")
      case _ =>
      // Handle other events
    }
  }
}