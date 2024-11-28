package me.timelytask.model.state

import me.timelytask.model.Task

class CompletedState extends TaskState {
  override def start(task: Task): Task = {
    println("Cannot start a completed task.")
    task
  }

  override def complete(task: Task): Task = {
    println("Task is already completed.")
    task
  }

  override def cancel(task: Task): Task = {
    println("Cannot cancel a completed task.")
    task
  }
}