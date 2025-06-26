package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.priority.Priority
import me.timelytask.serviceLayer.PriorityService

import java.util.UUID

class PriorityServiceImpl extends PriorityService {
  
  override def getPriority(userName: String, uuid: UUID): Priority = ???

  override def addPriority(userName: String, priority: Priority): Unit = ???

  override def removePriority(userName: String, uuid: UUID): Unit = ???

  override def updatePriority(userName: String, priority: Priority): Unit = ???

  override def getAllPriorities(userName: String): Seq[Priority] = ???
}
