package me.timelytask.model.state

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Task

class ClosedState extends TaskState {
  override def start(task: Task): Task = {
    // Do nothing
    // a closed task cannot be started
    task
  }

  override def complete(task: Task): Task = {
    // Do nothing
    // a closed task is already completed
    task
  }

  override def cancel(task: Task): Task = {
    // Do nothing
    // a closed task cannot be canceled
    task
  }

  override def extendDeadline(task: Task, extension: Period): Task = {
    // Do nothing
    // a closed task cannot have its deadline extended
    task
  }
}