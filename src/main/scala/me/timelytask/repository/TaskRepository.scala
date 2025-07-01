package me.timelytask.repository

import me.timelytask.model.task.Task

import java.util.UUID

trait TaskRepository {
  def getAllTasks(userName: String): Seq[Task]
  def getTaskById(userName: String, taskId: UUID): Task
  def addTask(userName: String, task: Task): Unit
  def deleteTask(userName: String, taskId: UUID): Unit
  def deleteAllTasks(userName: String): Unit
  def updateTask(userName: String, taskId: UUID, updatedTask: Task): Unit
}