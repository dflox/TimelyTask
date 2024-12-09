// src/main/scala/me/timelytask/model/TaskBuilder.scala
package me.timelytask.model.builder

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.{Deadline, Task}
import me.timelytask.model.state.*

import java.util.UUID
import scala.collection.immutable.HashSet

class TaskBuilder(defaultInstance: Task = Task()) extends Builder[Task](defaultInstance) {
  private var name: String = defaultInstance.name
  private var description: String = defaultInstance.description
  private var priority: UUID = defaultInstance.uuid
  private var tags: HashSet[UUID] = defaultInstance.tags
  private var deadline: Deadline = defaultInstance.deadline
  private var scheduleDate: DateTime = defaultInstance.scheduleDate
  private var state: TaskState = defaultInstance.state
  private var tedDuration: Period = defaultInstance.tedDuration
  private var dependentOn: HashSet[UUID] = defaultInstance.dependentOn
  private var reoccurring: Boolean = defaultInstance.reoccurring
  private var recurrenceInterval: Period = defaultInstance.recurrenceInterval

  def setName(name: String): TaskBuilder = {
    this.name = name
    this
  }

  def setDescription(description: String): TaskBuilder = {
    this.description = description
    this
  }

  def setPriority(priority: UUID): TaskBuilder = {
    this.priority = priority
    this
  }

  def setTags(tags: HashSet[UUID]): TaskBuilder = {
    this.tags = tags
    this
  }

  def setDeadline(deadline: Deadline): TaskBuilder = {
    this.deadline = deadline
    this
  }

  def setScheduleDate(scheduleDate: DateTime): TaskBuilder = {
    this.scheduleDate = scheduleDate
    this
  }

  def setState(state: TaskState): TaskBuilder = {
    this.state = state
    this
  }

  def setTedDuration(tedDuration: Period): TaskBuilder = {
    this.tedDuration = tedDuration
    this
  }

  def setDependentOn(dependentOn: HashSet[UUID]): TaskBuilder = {
    this.dependentOn = dependentOn
    this
  }

  def setReoccurring(reoccurring: Boolean): TaskBuilder = {
    this.reoccurring = reoccurring
    this
  }

  def setRecurrenceInterval(recurrenceInterval: Period): TaskBuilder = {
    this.recurrenceInterval = recurrenceInterval
    this
  }

  override def build(): Task = {
    Task(name, description, priority, tags, deadline, scheduleDate, state, tedDuration, dependentOn, reoccurring, recurrenceInterval)
  }
}