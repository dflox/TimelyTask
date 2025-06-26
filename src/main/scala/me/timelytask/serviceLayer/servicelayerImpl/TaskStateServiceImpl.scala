package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.state.TaskState
import me.timelytask.serviceLayer.TaskStateService

import java.util.UUID

class TaskStateServiceImpl extends TaskStateService {

  override def getTaskState(userName: String, uuid: UUID): TaskState = ???

  override def addTaskState(userName: String, taskState: TaskState): Unit = ???

  override def removeTaskState(userName: String, uuid: UUID): Unit = ???

  override def updateTaskState(userName: String, taskState: TaskState): Unit = ???

  override def getAllTaskStates(): Seq[String] = ???
}
