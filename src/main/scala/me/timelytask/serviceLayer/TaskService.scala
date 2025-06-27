package me.timelytask.serviceLayer

import com.github.nscala_time.time.Imports.{DateTime, Period}
import me.timelytask.model.deadline.Deadline
import me.timelytask.model.task.Task

import java.util.UUID
import scala.concurrent.Future

trait TaskService {
  def newTask(userName: String, task: Task): Unit

  def deleteTask(userName: String, taskUUID: UUID): Unit

  def updateName(userName: String, taskUUID: UUID, newName: String): Unit

  def updateDescription(userName: String, taskUUID: UUID, newDescription: String): Unit

  def updateScheduleDate(userName: String, taskUUID: UUID, newScheduleDate: DateTime): Unit

  def updateState(userName: String, taskUUID: UUID, newState: UUID): Unit

  def updatePriority(userName: String, taskUUID: UUID, newPriority: UUID): Unit

  def addTag(userName: String, taskUUID: UUID, tagUUID: UUID): Unit

  def removeTag(userName: String, taskUUID: UUID, tagUUID: UUID): Unit

  def updateDeadline(userName: String, taskUUID: UUID, newDeadline: Deadline): Unit

  def updateTedDuration(userName: String, taskUUID: UUID, newTedDuration: Period): Unit

  def addDependentTask(userName: String, taskUUID: UUID, dependentTaskUUID: UUID): Unit

  def removeDependentTask(userName: String, taskUUID: UUID, dependentTaskUUID: UUID): Unit

  def updateReoccurring(userName: String, taskUUID: UUID, newReoccurring: Boolean): Unit

  def updateRecurrenceInterval(userName: String, taskUUID: UUID,
                               recurrenceInterval: Period): Unit

  def updateRealDuration(userName: String, taskUUID: UUID,
                         newRealDuration: Option[Period]): Unit
  
  private[serviceLayer] def loadAllTasks(userName: String): Seq[Task]
}
