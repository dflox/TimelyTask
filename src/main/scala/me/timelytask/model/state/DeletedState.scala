package me.timelytask.model.state

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.task.Task
import scalafx.scene.paint.Color

import java.util.UUID

class DeletedState(name: String, description: String, color: Color, uuid: UUID = UUID.randomUUID())
  extends TaskState(name, description, color, uuid) {
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
object DeletedState {
  val stateType: String = "deleted"

  def apply(name: String, description: String, color: Color): DeletedState = {
    new DeletedState(name, description, color)
  }

  def apply(name: String, description: String, color: Color, uuid: UUID): DeletedState = {
    new DeletedState(name, description, color, uuid)
  }
}