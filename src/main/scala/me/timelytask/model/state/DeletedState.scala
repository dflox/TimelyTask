package me.timelytask.model.state

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Task

class DeletedState extends TaskState {
  override def start(task: Task): Task = {
    task.copy(state = new OpenState)
  }

  override def complete(task: Task): Task = {
    // Do nothing
    // a task that is deleted cannot be completed
    task
  }

  override def cancel(task: Task): Task = {
    // Do nothing
    // a task that is already deleted cannot be cancelled again
    task
  }
  
  override def extendDeadline(task: Task, extension: Period): Task = {
    // Do nothing
    // a deleted task cannot have its deadline extended
    task
  }
}