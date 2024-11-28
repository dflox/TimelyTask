package me.timelytask.model.state

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Task

class OpenState extends TaskState {
  override def start(task: Task): Task = {
    // Do nothing
    // a task that is already open cannot be started again
    task
  }

  override def complete(task: Task): Task = {
    task.copy(state = new ClosedState)
  }

  override def cancel(task: Task): Task = {
    task.copy(state = new DeletedState)
  }

  override def extendDeadline(task: Task, extension: Period): Task = {
    val newDeadline = task.deadline.copy(date = task.deadline.date + extension)
    task.copy(deadline = newDeadline)
  }
}