package me.timelytask.model.state

import me.timelytask.model.Task

class CompletedState extends TaskState {
  override def start(task: Task): Unit = {
    println("Cannot start a completed task.")
  }

  override def complete(task: Task): Unit = {
    println("Task is already completed.")
  }

  override def cancel(task: Task): Unit = {
    println("Cannot cancel a completed task.")
  }
}