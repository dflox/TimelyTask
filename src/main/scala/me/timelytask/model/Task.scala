// src/main/scala/me/timelytask/model/Task.scala
package me.timelytask.model

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.state.{OpenState, TaskState}
import me.timelytask.model.builder.TaskBuilder

import java.util.UUID
import scala.collection.immutable.HashSet

case class Task(name: String = "",
                description: String = "",
                priority: UUID = null,
                tags: HashSet[UUID] = new HashSet[UUID](),
                deadline: Deadline = Deadline(DateTime.now() + 1.day, None, None),
                scheduleDate: DateTime = DateTime.now(),
                state: TaskState = new OpenState,
                tedDuration: Period = 1.hour.toPeriod,
                dependentOn: HashSet[UUID] = new HashSet[UUID](),
                reoccurring: Boolean = false,
                recurrenceInterval: Period = 1.week.toPeriod) {

  val uuid: UUID = UUID.randomUUID()
  val realDuration: Option[Period] = None
  val completionDate: Option[DateTime] = None

  def start(): Task = {
    // change the state of the task to started (State Pattern)
    state.start(this)
  }

  def complete(): Task = {
    state.complete(this)
  }

  def cancel(): Task = {
    state.cancel(this)
  }
  def extendDeadline(extension: Period): Task = state.extendDeadline(this, extension)
  
  def isValid: Option[String] = {
    if (name.isEmpty) {
      Some("Name cannot be empty.")
    } else if (priority == null) {
      Some("Please choose a priority for this task.")
    } else {
      None
    }
  }
}

object Task {
  val exampleTask : Task = TaskBuilder()
    .setName("Example Task")
    .setDescription("This is an example task")
    .setPriority(UUID.randomUUID())
    .setTags(HashSet(UUID.randomUUID()))
    .setDeadline(Deadline(DateTime.now(), None, None))
    .setScheduleDate(DateTime.now())
    .setState(new OpenState)
    .setTedDuration(1.hour.toPeriod)
    .setDependentOn(HashSet())
    .setReoccurring(false)
    .setRecurrenceInterval(1.week.toPeriod)
    .build()
}