package me.timelytask.model

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.builder.TaskBuilder
import me.timelytask.model.state.{ClosedState, DeletedState, OpenState, TaskState}

import java.util.UUID
import scala.collection.immutable.HashSet
import scala.util.{Try, Success, Failure}

case class Task(name: String = "",
                description: String = "",
                priority: Option[UUID] = None,
                tags: HashSet[UUID] = new HashSet[UUID](),
                deadline: Deadline = Deadline(DateTime.now() + 1.day, None, None),
                scheduleDate: DateTime = DateTime.now(),
                state: Option[UUID] = None,
                tedDuration: Period = 1.hour.toPeriod,
                dependentOn: HashSet[UUID] = new HashSet[UUID](),
                reoccurring: Boolean = false,
                recurrenceInterval: Period = 1.week.toPeriod) {

  val uuid: UUID = UUID.randomUUID()
  val realDuration: Option[Period] = None
  val completionDate: Option[DateTime] = None

  private def getState[TS <: TaskState](stateFinder: UUID => Option[TaskState], state: Option[UUID])
  : Option[TS] = {
    Try[Option[TS]]{
      state.map(s => stateFinder(s).asInstanceOf[TS])
    } match {
      case Success(value) => value
      case Failure(_) => None
    }
  }

  def start(stateFinder: UUID => Option[TaskState], openState: Option[UUID]): Option[Task] = {
    // change the state of the task to started (State Pattern)
    getState[OpenState](stateFinder, state) match
      case Some(s) => getState(stateFinder, openState) match
        case Some(os) => s.start(this, os)
        case None => None
      case None => None
  }

  def complete(stateFinder: UUID => Option[TaskState], closedState: Option[UUID]): Option[Task] = {
    // change the state of the task to completed (State Pattern)
    getState[ClosedState](stateFinder, state) match
      case Some(s) => getState(stateFinder, closedState) match
        case Some(cs) => s.complete(this, cs)
        case None => None
      case None => None
  }

  def delete(stateFinder: UUID => Option[TaskState], deletedState: Option[UUID]): Option[Task] = {
    // change the state of the task to deleted (State Pattern)
    getState[DeletedState](stateFinder, state) match
      case Some(s) => getState(stateFinder, deletedState) match
        case Some(ds) => s.delete(this, ds)
        case None => None
      case None => None
  }

  def extendDeadline(stateFinder: UUID => Option[TaskState], extension: Period): Option[Task] = {
    getState[TaskState](stateFinder, state) match
      case Some(s) => s.extendDeadline(this, extension)
      case None => None
  }

  def isValid: Option[String] = {
    if (name.isEmpty) {
      Some("Name cannot be empty.")
    } else if (priority.isEmpty) {
      Some("Please choose a priority for this task.")
    } else {
      None
    }
  }
}

object Task {
  val exampleTask: Task = TaskBuilder()
    .setName("Example Task")
    .setDescription("This is an example task")
    .setPriority(UUID.randomUUID())
    .setTags(HashSet(UUID.randomUUID()))
    .setDeadline(Deadline(DateTime.now(), None, None))
    .setScheduleDate(DateTime.now())
    .setState(None)
    .setTedDuration(1.hour.toPeriod)
    .setDependentOn(HashSet())
    .setReoccurring(false)
    .setRecurrenceInterval(1.week.toPeriod)
    .build()

  val descDescription: String = "Description"
  val descPriority: String = "Priority"
  val descTags: String = "Tags"
  val descDeadline: String = "Deadline"
  val descScheduleDate: String = "Schedule Date"
  val descState: String = "State"
  val descTedDuration: String = "Expected Duration"
  val descDependentOn: String = "Dependent On"
  val descReoccurring: String = "Reoccurring"
  val descRecurrenceInterval: String = "Recurrence Interval"
}