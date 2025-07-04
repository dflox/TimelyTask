package me.timelytask.model.state

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.task.Task
import scalafx.scene.paint.Color

import java.util.UUID

// TODO: Make TaskState a sealed trait to restrict its subclasses and make the subclasses case 
//  classes so the equals method can be removed
trait TaskState(val name: String, val description: String,
                val color: Color, val uuid: UUID = UUID.randomUUID()) {

  override def equals(obj: Any): Boolean = obj match {
    case that: TaskState => this.uuid == that.uuid
    case _ => false
  }

  def stateType: String = this.getClass.getSimpleName.stripSuffix("State").toLowerCase

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
      case OpenState.stateType => new OpenState(name, description, color, UUID)
      case ClosedState.stateType => new ClosedState(name, description, color, UUID)
      case DeletedState.stateType => new DeletedState(name, description, color, UUID)
      case _ => throw new IllegalArgumentException(s"Unknown state type: $stateType")
    }
  }
}