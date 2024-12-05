// src/main/scala/me/timelytask/model/TaskBuilder.scala
package me.timelytask.model.builder

import com.github.nscala_time.time.Imports.*
import me.timelytask.controller.TaskController
import me.timelytask.model.{Deadline, Task}
import me.timelytask.view.viewmodel.ViewModelStatus
import me.timelytask.model.state.*
import me.timelytask.model.settings.activeViewPublisher
import me.timelytask.TimelyTask.viewModelPublisher
import me.timelytask.TimelyTask.modelPublisher

import java.util.UUID
import scala.collection.immutable.HashSet

class TaskBuilder {
  private var name: String = ""
  private var description: String = ""
  private var priority: UUID = UUID.randomUUID()
  private var tags: HashSet[UUID] = HashSet()
  private var deadline: Deadline = Deadline(DateTime.now(), None, None)
  private var scheduleDate: DateTime = DateTime.now()
  private var state: TaskState = new OpenState
  private var tedDuration: Period = 0.seconds.toPeriod
  private var dependentOn: HashSet[UUID] = HashSet()
  private var reoccurring: Boolean = false
  private var recurrenceInterval: Period = 0.seconds.toPeriod

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

  def build(): Task = {
    Task(name, description, priority, tags, deadline, scheduleDate, state, tedDuration, dependentOn, reoccurring, recurrenceInterval)
  }
}