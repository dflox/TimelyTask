package me.timelytask.model.task

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.builder.TaskBuilder
import me.timelytask.model.deadline.Deadline
import me.timelytask.model.state.{ClosedState, DeletedState, OpenState, TaskState}

import java.util.UUID
import scala.collection.immutable.HashSet
import scala.util.{Failure, Success, Try}

/**
 * Represents a task in the system.
 * @param name the name of the task
 * @param description the description of the task
 * @param priority the UUID of the priority assigned to the task
 * @param tags a set of UUIDs representing tags associated with the task
 * @param deadline the deadline for the task, including optional start and end times
 * @param scheduleDate the date and time when the task is scheduled
 * @param state the UUID of the current state of the task
 * @param tedDuration the expected duration of the task, represented as a Period
 * @param dependentOn a set of UUIDs representing tasks that this task depends on and must be completed before it can start
 * @param reoccurring indicates whether the task is reoccurring
 * @param recurrenceInterval the interval at which the task recurs, represented as a Period
 * @param uuid the unique identifier for the task
 * @param realDuration the actual duration the task took to complete, represented as an optional 
 *                     Period
 */
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
                recurrenceInterval: Period = 1.week.toPeriod,
                uuid: UUID = UUID.randomUUID(),
                realDuration: Option[Period] = None) {

  private def getState[TS <: TaskState](stateFinder: UUID => Option[TaskState], state: Option[UUID])
  : Option[TS] = {
    Try[Option[TS]] {
      state.map(s => stateFinder(s).asInstanceOf[TS])
    } match {
      case Success(value) => value
      case Failure(_) => None
    }
  }

  /**
   * Starts the task by changing its state to started.
   * @param stateFinder a function that finds the task state by its UUID
   * @param openState   an optional UUID representing the open state
   * @return An `Option[Task]` containing the updated task if the start was successful, or `None` if it failed.
   */
  def start(stateFinder: UUID => Option[TaskState], openState: Option[UUID]): Option[Task] = {
    // change the state of the task to started (State Pattern)
    getState[OpenState](stateFinder, state) match
      case Some(s) => getState(stateFinder, openState) match
        case Some(os) => s.start(this, os)
        case None => None
      case None => None
  }

  /**
   * Completes the task by changing its state to completed.
   * @param stateFinder a function that finds the task state by its UUID
   * @param closedState an optional UUID representing the closed state
   * @return An `Option[Task]` containing the updated task if the completion was successful, or `None` if it failed.
   */
  def complete(stateFinder: UUID => Option[TaskState], closedState: Option[UUID]): Option[Task] = {
    // change the state of the task to completed (State Pattern)
    getState[ClosedState](stateFinder, state) match
      case Some(s) => getState(stateFinder, closedState) match
        case Some(cs) => s.complete(this, cs)
        case None => None
      case None => None
  }

  /**
   * Deletes the task by changing its state to deleted.
   * @param stateFinder a function that finds the task state by its UUID
   * @param deletedState an optional UUID representing the deleted state
   * @return An `Option[Task]` containing the updated task if the deletion was successful, or `None` if it failed.
   */
  def delete(stateFinder: UUID => Option[TaskState], deletedState: Option[UUID]): Option[Task] = {
    // change the state of the task to deleted (State Pattern)
    getState[DeletedState](stateFinder, state) match
      case Some(s) => getState(stateFinder, deletedState) match
        case Some(ds) => s.delete(this, ds)
        case None => None
      case None => None
  }

  /**
   * Validates the task to ensure it has a name and priority.
   * @return An `Option[String]` containing an error message if the task is invalid, or `None` if it is valid.
   */
  def isValid: Option[String] = {
    if (name.isEmpty) {
      Some("Name cannot be empty.")
    } else if (priority.isEmpty) {
      Some("Please choose a priority for this task.")
    } else {
      None
    }
  }

  /**
   * @inheritdoc
   */
  override def equals(obj: Any): Boolean = obj match {
    case that: Task => this.uuid == that.uuid
    case _ => false
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