package me.timelytask.model.state

import me.timelytask.model.Task

class InProgressState extends TaskState {
  override def start(task: Task): Unit = {
    println("Task is already in progress.")
  }

  override def complete(task: Task): Unit = {
    println("Task completed.")
  }

  override def cancel(task: Task): Unit = {
    println("Task cancelled.")
  }
}
