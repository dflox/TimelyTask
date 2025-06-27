package me.timelytask.controller

import me.timelytask.model.task.Task

trait ModelController {
  def addTask(userToken: String, task: Task): Unit
  
  def removeTask(userToken: String, task: Task): Unit
  
  // TODO: add, remove, update: tag, priority, state/status
}
