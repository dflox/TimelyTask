package me.timelytask.serviceLayer

import me.timelytask.model.Model
import me.timelytask.model.config.Config
import me.timelytask.model.priority.Priority
import me.timelytask.model.state.TaskState
import me.timelytask.model.tag.Tag
import me.timelytask.model.task.Task

import scala.collection.immutable.HashSet

trait UpdateService {
  def registerModelUpdateListener(listener: (userName: String, model: Model) => Unit ): Unit
  def registerTaskUpdateListener(listener: (userName: String, task: Task) => Unit): Unit
  def registerTagUpdateListener(listener: (userName: String, tag: Tag) => Unit): Unit
  def registerPriorityUpdateListener(listener: (userName: String, priority: Priority) => Unit): Unit
  def registerTaskStateUpdateListener(listener: (userName: String, state: TaskState) => Unit): Unit
  def registerConfigUpdateListener(listener: (userName: String, config: Config) => Unit): Unit

  private[serviceLayer] def updateModel(userName: String, model: Model): Unit
  private[serviceLayer] def updateTask(userName: String, updatedTask: Task): Unit
  private[serviceLayer] def updateTags(userName: String, updatedTag: Tag): Unit
  private[serviceLayer] def updatePriorities(userName: String, updatedPriority: Priority): Unit
  private[serviceLayer] def updateTaskStates(userName: String, updatedTaskState: TaskState): Unit
  private[serviceLayer] def updateConfig(userName: String, updatedConfig: Config): Unit
}
