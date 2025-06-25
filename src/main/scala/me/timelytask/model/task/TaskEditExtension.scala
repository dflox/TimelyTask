package me.timelytask.model.task

import com.github.nscala_time.time.Imports.{DateTime, Period}
import me.timelytask.model.deadline.Deadline

import java.util.UUID
import scala.collection.immutable.HashSet

extension (task: Task) {
  /**
   * Sets the title of the task.
   *
   * @param name The title to set for the task.
   * @return Some(Task) if the title is valid, None if the title is empty.
   */
  def withName(name: String): Task = {
    if (name.isEmpty) {
      throw new IllegalArgumentException("The task name cannot be empty.")
    }
    task.copy(name = name)
  }

  /**
   * Sets the description of the task.
   *
   * @param description The description to set for the task.
   * @return Task with the updated description.
   */
  def withDescription(description: String): Task = {
    task.copy(description = description)
  }

  /**
   * Sets the priority of the task.
   *
   * @param priority The UUID of the priority to set for the task.
   * @return Task with the updated priority.
   */
  def withPriority(priority: Option[UUID]): Task = {
    task.copy(priority = priority)
  }

  /**
   * Adds a tag to the task.
   *
   * @param tag The UUID of the tag to add.
   * @return Task with the updated tags.
   */
  def addTag(tag: UUID): Task = {
    task.copy(tags = task.tags + tag)
  }

  /**
   * Removes a tag from the task.
   *
   * @param tag The UUID of the tag to remove.
   * @return Task with the updated tags.
   */
  def removeTag(tag: UUID): Task = {
    task.copy(tags = task.tags - tag)
  }

  /**
   * Removes all tags from the task.
   *
   * @return Task with no tags.
   */
  def removeAllTags(): Task = {
    task.copy(tags = HashSet[UUID]())
  }

  /**
   * Sets the deadline for the task.
   *
   * @param deadline The Deadline object to set for the task.
   * @return Task with the updated deadline.
   */
  def withDeadline(deadline: Deadline): Task = {
    task.copy(deadline = deadline)
  }

  /**
   * Sets the schedule date for the task.
   *
   * @param scheduleDate The DateTime to set as the schedule date for the task.
   * @return Task with the updated schedule date.
   */
  def withScheduleDate(scheduleDate: DateTime): Task = {
    task.copy(scheduleDate = scheduleDate)
  }

  /**
   * Sets the state of the task.
   *
   * @param state The UUID of the state to set for the task.
   * @return Task with the updated state.
   */
  def withState(state: Option[UUID]): Task = {
    task.copy(state = state)
  }

  /**
   * Sets the expected duration for the task.
   *
   * @param tedDuration The Period to set as the expected duration for the task.
   * @return Task with the updated expected duration.
   */
  def withTedDuration(tedDuration: Period): Task = {
    task.copy(tedDuration = tedDuration)
  }

  /**
   * Adds a dependent task.
   *
   * @param dependent The UUID of the dependent task to add.
   * @return Task with the updated dependents.
   */
  def addDependentOn(dependent: UUID): Task = {
    task.copy(dependentOn = task.dependentOn + dependent)
  }

  /**
   * Removes a dependent task.
   *
   * @param dependent The UUID of the dependent task to remove.
   * @return Task with the updated dependents.
   */
  def removeDependentOn(dependent: UUID): Task = {
    task.copy(dependentOn = task.dependentOn - dependent)
  }

  /**
   * Removes all dependent tasks.
   *
   * @return Task with no dependents.
   */
  def removeAllDependents(): Task = {
    task.copy(dependentOn = HashSet[UUID]())
  }

  /**
   * Sets whether the task is reoccurring.
   *
   * @param reoccurring Boolean indicating if the task is reoccurring.
   * @return Task with the updated reoccurring status.
   */
  def withReoccurring(reoccurring: Boolean): Task = {
    task.copy(reoccurring = reoccurring)
  }

  /**
   * Sets the recurrence interval for the task.
   *
   * @param recurrenceInterval The Period to set as the recurrence interval for the task.
   * @return Task with the updated recurrence interval.
   */
  def withRecurrenceInterval(recurrenceInterval: Period): Task = {
    task.copy(recurrenceInterval = recurrenceInterval)
  }

  /**
   * Sets the real duration of the task.
   *
   * @param realDuration The Period to set as the real duration for the task.
   * @return Task with the updated real duration.
   */
  def withRealDuration(realDuration: Option[Period]): Task = {
    task.copy(realDuration = realDuration)
  }

  /**
   * Sets a UUID for the task.
   *
   * @param uuid The UUID to set for the task.
   * @return Task with the updated UUID.
   * @note Use with caution, as UUIDs are typically generated automatically.
   */
  def withUUID(uuid: UUID): Task = {
    task.copy(uuid = uuid)
  }
}