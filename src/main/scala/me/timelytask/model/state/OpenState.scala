package me.timelytask.model.state

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Task
import scalafx.scene.paint.Color

class OpenState(name: String, description: String, color: Color) extends TaskState(name,
  description, color) {
  override def start(task: Task, openState: OpenState): Option[Task] = {
    // Do nothing
    // a task that is already open cannot be started again
    None
  }

  override def complete(task: Task, closedState: ClosedState): Option[Task] = {
    Some(task.copy(state = Some(closedState.uuid)))
  }

  override def delete(task: Task, deletedState: DeletedState): Option[Task] = {
    Some(task.copy(state = Some(deletedState.uuid)))
  }

  override def extendDeadline(task: Task, extension: Period): Option[Task] = {
    val newDeadline = task.deadline.copy(date = task.deadline.date + extension)
    Some(task.copy(deadline = newDeadline))
  }
}