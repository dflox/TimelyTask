package me.timelytask.testUtil

import com.softwaremill.macwire.wire
import me.timelytask.repository.{ TaskRepository, UserRepository }
import me.timelytask.serviceLayer.servicelayerImpl.*
import me.timelytask.serviceLayer.*
import org.scalatestplus.mockito.MockitoSugar.mock

class ServiceModuleBuilder {
  private var userRepository: Option[UserRepository] = None
  private var taskRepository: Option[TaskRepository] = None

  def withUserRepository(
      userRepository: UserRepository
    ): ServiceModuleBuilder = {
    this.userRepository = Some(userRepository)
    this
  }

  def withTaskRepository(
      taskRepository: TaskRepository
    ): ServiceModuleBuilder = {
    this.taskRepository = Some(taskRepository)
    this
  }

  def build(): ServiceModule = {
    new ServiceModule {
      private val userRepo = userRepository.getOrElse(mock[UserRepository])
      private val taskRepo = taskRepository.getOrElse(mock[TaskRepository])
      private lazy val self: ServiceModule = this
      
      override val taskService: TaskService = wire[TaskServiceImpl]
      override val configService: ConfigService = wire[ConfigServiceImpl]
      override val priorityService: PriorityService = wire[PriorityServiceImpl]
      override val tagService: TagService = wire[TagServiceImpl]
      override val taskStateService: TaskStateService =
        wire[TaskStateServiceImpl]

      override val userService: UserService = wire[UserServiceImpl]

      override val fileExportService: FileExportService =
        wire[FileExportServiceImpl]
      override val updateService: UpdateService = wire[UpdateServiceImpl]
      override val modelService: ModelService = wire[ModelServiceImpl]
    }
  }

}
