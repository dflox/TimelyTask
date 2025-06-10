package me.timelytask.model.state

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Task
import scalafx.scene.paint.Color

import java.util.UUID

trait TaskState(val name: String, val description: String, val color: Color, val uuid: UUID = UUID.randomUUID()) {

  def start(task: Task, openState: OpenState): Option[Task]

  def complete(task: Task, closedState: ClosedState): Option[Task]

  def delete(task: Task, deletedState: DeletedState): Option[Task]

  def extendDeadline(task: Task, extension: Period): Option[Task]
}
object TaskState {
  def apply(name: String, description: String, color: Color): TaskState = {
    new OpenState(name, description, color)
  }

  def apply(name: String, description: String, color: Color, stateType: String, UUID: UUID): TaskState = {
    stateType match {
      case "open" => new OpenState(name, description, color, UUID)
      case "closed" => new ClosedState(name, description, color, UUID)
      case "deleted" => new DeletedState(name, description, color, UUID)
      case _ => throw new IllegalArgumentException(s"Unknown state type: $stateType")
    }
  }
}