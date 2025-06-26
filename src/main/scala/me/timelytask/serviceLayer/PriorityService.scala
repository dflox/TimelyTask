package me.timelytask.serviceLayer

import me.timelytask.model.priority.Priority

import java.util.UUID

trait PriorityService {
  def getPriority(userName: String, uuid: UUID): Priority
  def addPriority(userName: String, priority: Priority): Unit
  def removePriority(userName: String, uuid: UUID): Unit
  def updatePriority(userName: String, priority: Priority): Unit
  def getAllPriorities(userName: String): Seq[Priority]
}
