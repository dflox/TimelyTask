package me.timelytask.model.state

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Task
import scalafx.scene.paint.Color

class ClosedState(name: String, description: String, color: Color) extends TaskState(name, description, color) {
  override def start(task: Task, openState: OpenState): Option[Task] = {
    // Do nothing
    // a closed task cannot be started
    None
  }

  override def complete(task: Task, closedState: ClosedState): Option[Task] = {
    // Do nothing
    // a closed task is already completed
    Some(task.copy(state = Some(closedState.uuid)))
  }

  override def delete(task: Task, deletedState: DeletedState): Option[Task] = {
    // Do nothing
    // a closed task cannot be canceled
    None
  }

  override def extendDeadline(task: Task, extension: Period): Option[Task] = {
    // Do nothing
    // a closed task cannot have its deadline extended
    None
  }
}