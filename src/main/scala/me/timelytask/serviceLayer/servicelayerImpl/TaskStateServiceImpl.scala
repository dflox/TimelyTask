package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.state.{OpenState, TaskState}
import me.timelytask.serviceLayer.TaskStateService
import scalafx.scene.paint.Color

import java.util.UUID

class TaskStateServiceImpl extends TaskStateService {

  override def getTaskState(userName: String, uuid: UUID): TaskState = {
    OpenState("Default Task State", "This is a default task state", Color.Red)
  }

  override def addTaskState(userName: String, taskState: TaskState): Unit = ()

  override def removeTaskState(userName: String, uuid: UUID): Unit = ()

  override def updateTaskState(userName: String, taskState: TaskState): Unit = ()

  override def getAllTaskStates(userName: String): Seq[TaskState] = Seq.empty
}
