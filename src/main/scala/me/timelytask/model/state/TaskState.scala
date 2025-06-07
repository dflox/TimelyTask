package me.timelytask.model.state

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Task
import scalafx.scene.paint.Color

import java.util.UUID

trait TaskState(val name: String, val description: String, val color: Color) {
  val uuid: UUID = UUID.randomUUID()

  def start(task: Task, openState: OpenState): Option[Task]

  def complete(task: Task, closedState: ClosedState): Option[Task]

  def delete(task: Task, deletedState: DeletedState): Option[Task]

  def extendDeadline(task: Task, extension: Period): Option[Task]
}