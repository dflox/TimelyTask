package me.timelytask.serviceLayer

import me.timelytask.model.state.TaskState

import java.util.UUID

trait TaskStateService {
  def getTaskState(userName: String, uuid: UUID): TaskState
  def addTaskState(userName: String, taskState: TaskState): Unit
  def removeTaskState(userName: String, uuid: UUID): Unit
  def updateTaskState(userName: String, taskState: TaskState): Unit
  def getAllTaskStates(userName: String): Seq[TaskState]
}
