package me.timelytask.serviceLayer

import me.timelytask.model.config.Config
import me.timelytask.model.priority.Priority
import me.timelytask.model.state.TaskState
import me.timelytask.model.tag.Tag
import me.timelytask.model.task.Task
import me.timelytask.util.Publisher

import scala.collection.immutable.HashSet

trait ServiceModule() {
  private[serviceLayer] val taskPublisher: Publisher[List[Task]]
  private[serviceLayer] val configPublisher: Publisher[Config]
  private[serviceLayer] val priorityPublisher: Publisher[HashSet[Priority]]
  private[serviceLayer] val tagPublisher: Publisher[HashSet[Tag]]
  private[serviceLayer] val taskStatePublisher: Publisher[HashSet[TaskState]]

  val taskService: TaskService
  val configService: ConfigService
  val priorityService: PriorityService
  val tagService: TagService
  val taskStateService: TaskStateService
  
  val userService: UserService
  val fileExportService: FileExportService
}
