package me.timelytask.model.state

import me.timelytask.model.Task

class CancelledState extends TaskState {
  override def start(task: Task): Task = {
    // soll es möglich sein eine abgebrochene Task wieder zu starten?
    task.copy(state = new InProgressState)
  }

  override def complete(task: Task): Task = {
    //throw new IllegalStateException("Cannot complete a cancelled task.")
    task
  }

  override def cancel(task: Task): Task = {
    //throw new IllegalStateException("Task is already cancelled.")
    task
  }
}