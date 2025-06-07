package me.timelytask.model.state

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Task
import scalafx.scene.paint.Color

class DeletedState(name: String, description: String, color: Color) extends TaskState(name,
  description, color) {
  override def start(task: Task, openState: OpenState): Option[Task] = {
    Some(task.copy(state = Some(openState.uuid)))
  }

  override def complete(task: Task, closedState: ClosedState): Option[Task] = {
    // Do nothing
    // a task that is deleted cannot be completed
    None
  }

  override def delete(task: Task, deletedState: DeletedState): Option[Task] = {
    Some(task.copy(state = Some(deletedState.uuid)))
  }

  override def extendDeadline(task: Task, extension: Period): Option[Task] = {
    // Do nothing
    // a deleted task cannot have its deadline extended
    None
  }
}