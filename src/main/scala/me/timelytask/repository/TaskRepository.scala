package me.timelytask.repository

import com.github.nscala_time.time.Imports.{DateTime, Period}
import me.timelytask.model.deadline.Deadline
import me.timelytask.model.task.Task

import java.util.UUID

trait TaskRepository {
  def getTaskById(userName: String, taskId: UUID): Task

  def save(userName: String, task: Task): Unit

  def updateTitle(userName: String, taskId: UUID, newTitle: String): Unit

  def updateDescription(userName: String, taskId: UUID, newDescription: String): Unit

  def updatePriority(userName: String, taskId: UUID, newPriority: UUID): Unit

  def updateTags(userName: String, taskId: UUID, newTags: Set[UUID]): Unit

  def updateDeadline(userName: String, taskId: UUID, newDeadline: Deadline): Unit

  def updateScheduleDate(userName: String, taskId: UUID, newScheduleDate: DateTime): Unit

  def updateState(userName: String, taskId: UUID, newState: Option[UUID]): Unit

  def updateTedDuration(userName: String, taskId: UUID, newTedDuration: Period): Unit

  def updateDependentOn(userName: String, taskId: UUID, newDependentOn: Set[UUID]): Unit

  def updateReoccurring(userName: String, taskId: UUID, newRecurring: Boolean): Unit

  def updateReoccurrenceInterval(userName: String, taskId: UUID, newReoccurrenceInterval: Period): Unit

  def updateUUID(userName: String, taskId: UUID, newUUID: UUID): Unit

  def updateRealDuration(userName: String, taskId: UUID, newRealDuration: Option[Period]): Unit
}