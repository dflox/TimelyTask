package me.timelytask.model.state

import me.timelytask.model.Task

class CancelledState extends TaskState {
  override def start(task: Task): Task = {
    println("Cannot start a cancelled task.")
    task
  }

  override def complete(task: Task): Task = {
    println("Cannot complete a cancelled task.")
    task
  }

  override def cancel(task: Task): Task = {
    println("Task is already cancelled.")
    task
  }
}