package me.timelytask.model.state

import me.timelytask.model.Task

class InProgressState extends TaskState {
  override def start(task: Task): Task = {
    println("Task is already in progress.")
    task
  }

  override def complete(task: Task): Task = {
    println("Task completed.")
    task.copy(state = new CompletedState)
  }

  override def cancel(task: Task): Task = {
    println("Task cancelled.")
    task.copy(state = new CancelledState)
  }
}