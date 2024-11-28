package me.timelytask.model.state

import me.timelytask.model.Task

class CancelledState extends TaskState {
  override def start(task: Task): Unit = {
    println("Cannot start a cancelled task.")
  }

  override def complete(task: Task): Unit = {
    println("Cannot complete a cancelled task.")
  }

  override def cancel(task: Task): Unit = {
    println("Task is already cancelled.")
  }
}