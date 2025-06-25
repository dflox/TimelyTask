package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.config.Config
import me.timelytask.model.priority.Priority
import me.timelytask.model.state.TaskState
import me.timelytask.model.tag.Tag
import me.timelytask.model.task.Task
import me.timelytask.serviceLayer.{ConfigService, FileExportService, PriorityService, ServiceModule, TagService, TaskService, TaskStateService, UserService}
import me.timelytask.util.Publisher

import scala.collection.immutable.HashSet

class ServiceModuleImpl extends ServiceModule {
  private lazy val self: ServiceModule = this
  
  override private[serviceLayer] val taskPublisher: Publisher[List[Task]] = ???
  override private[serviceLayer] val configPublisher: Publisher[Config] = ???
  override private[serviceLayer] val priorityPublisher: Publisher[HashSet[Priority]] = ???
  override private[serviceLayer] val tagPublisher: Publisher[HashSet[Tag]] = ???
  override private[serviceLayer] val taskStatePublisher: Publisher[HashSet[TaskState]] = ???
  
  override val taskService: TaskService = ???
  override val configService: ConfigService = ???
  override val priorityService: PriorityService = ???
  override val tagService: TagService = ???
  override val taskStateService: TaskStateService = ???
  
  override val userService: UserService = ???
  override val fileExportService: FileExportService = ???
}
