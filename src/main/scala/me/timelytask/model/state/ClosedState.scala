package me.timelytask.model.state

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.task.Task
import scalafx.scene.paint.Color

import java.util.UUID

class ClosedState(name: String, description: String, color: Color, uuid: UUID = UUID.randomUUID())
  extends TaskState(name, description, color, uuid) {
  
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
object ClosedState {
  val stateType: String = "closed"
  
  def apply(name: String, description: String, color: Color): ClosedState = {
    new ClosedState(name, description, color)
  }
  
  def apply(name: String, description: String, color: Color, uuid: UUID): ClosedState = {
    new ClosedState(name, description, color, uuid)
  }
}