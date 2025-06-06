package me.timelytask.controller

import me.timelytask.model.Task

trait ModelController {
  def addTask(task: Task): Unit
  
  def removeTask(task: Task): Unit
  
  def updateTask(task: Task): Unit
  
  // TODO: add, remove, update: tag, priority, state/status
}
