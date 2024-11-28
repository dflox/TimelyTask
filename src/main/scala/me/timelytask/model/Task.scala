package me.timelytask.model

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.state.{TaskState, OpenState}

import java.util.UUID
import scala.collection.immutable.HashSet

case class Task(name: String,
                description: String,
                priority: UUID,
                tags: HashSet[UUID] = new HashSet[UUID](),
                deadline: Deadline,
                scheduleDate: DateTime,
                state: TaskState = new OpenState,
                tedDuration: Period,
                dependentOn: HashSet[UUID] = new HashSet[UUID](),
                reoccurring: Boolean,
                recurrenceInterval: Period) {

  val uuid: UUID = UUID.randomUUID()
  val realDuration: Option[Period] = None
  val completionDate: Option[DateTime] = None

  def start(): Task = state.start(this)
  def complete(): Task = state.complete(this)
  def cancel(): Task = state.cancel(this)
  def extendDeadline(extension: Period): Task = state.extendDeadline(this, extension)
}

object Task {
  val exampleTask: Task = Task("ExTask", "This is an example task",
    UUID.randomUUID(), HashSet(UUID.randomUUID()), Deadline(DateTime.now(), None, None),
    DateTime.now(), new OpenState, 1.hour, HashSet(UUID.randomUUID()), false, 1.hour)
  val emptyTask: Task = Task("", "", UUID.randomUUID(), new HashSet[UUID](), Deadline(DateTime.now(), None, None),
    DateTime.now(), new OpenState, 1.hour, new HashSet[UUID](), false, 1.hour)
}