package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.Model
import me.timelytask.model.config.Config
import me.timelytask.model.priority.Priority
import me.timelytask.model.state.TaskState
import me.timelytask.model.tag.Tag
import me.timelytask.model.task.Task
import me.timelytask.serviceLayer.UpdateService

class UpdateServiceImpl extends UpdateService{
  private var modelUpdateListener: (String, Model) => Unit = (_, _) => ()
  private var taskUpdateListener: (String, Task) => Unit = (_, _) => ()
  private var tagUpdateListener: (String, Tag) => Unit = (_, _) => ()
  private var priorityUpdateListener: (String, Priority) => Unit = (_, _) => ()
  private var taskStateUpdateListener: (String, TaskState) => Unit = (_, _) => ()
  private var configUpdateListener: (String, Config) => Unit = (_, _) => ()

  override def registerModelUpdateListener(listener: (userName: String, model: Model) => Unit)
  : Unit = modelUpdateListener = listener

  override def registerTaskUpdateListener(listener: (userName: String, task: Task) => Unit): Unit =
    taskUpdateListener = listener

  override def registerTagUpdateListener(listener: (userName: String, tag: Tag) => Unit): Unit =
    tagUpdateListener = listener

  override def registerPriorityUpdateListener(listener: (userName: String, priority: Priority) =>
    Unit): Unit = priorityUpdateListener = listener

  override def registerTaskStateUpdateListener(listener: (userName: String, state: TaskState) => 
    Unit): Unit = taskStateUpdateListener = listener

  override def registerConfigUpdateListener(listener: (userName: String, config: Config) => Unit)
  : Unit = configUpdateListener = listener

  override private[serviceLayer] def updateModel(userName: String, model: Model): Unit = {
    modelUpdateListener(userName, model)
  }

  override private[serviceLayer] def updateTask(userName: String, updatedTask: Task): Unit = {
    taskUpdateListener(userName, updatedTask)
  }

  override private[serviceLayer] def updateTags(userName: String, updatedTag: Tag): Unit = {
    tagUpdateListener(userName, updatedTag)
  }

  override private[serviceLayer] def updatePriorities(userName: String, updatedPriority: Priority): Unit = {
    priorityUpdateListener(userName, updatedPriority)
  }

  override private[serviceLayer] def updateTaskStates(userName: String, updatedTaskState: TaskState): Unit = {
    taskStateUpdateListener(userName, updatedTaskState)
  }

  override private[serviceLayer] def updateConfig(userName: String, updatedConfig: Config): Unit = {
    configUpdateListener(userName, updatedConfig)
  }
}
