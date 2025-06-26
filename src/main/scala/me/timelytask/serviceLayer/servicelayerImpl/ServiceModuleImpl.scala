package me.timelytask.serviceLayer.servicelayerImpl

import com.softwaremill.macwire.wire
import me.timelytask.serviceLayer.*
import simplesql.DataSource

class ServiceModuleImpl extends ServiceModule {
  private val dataSource: DataSource = DataSource.pooled("jbdc:sqlite:TimelyTaskDataStore")
  private lazy val self: ServiceModule = this
  
  override val taskService: TaskService = wire[TaskServiceImpl]
  override val configService: ConfigService = wire[ConfigServiceImpl]
  override val priorityService: PriorityService = wire[PriorityServiceImpl]
  override val tagService: TagService = wire[TagServiceImpl]
  override val taskStateService: TaskStateService = wire[TaskStateServiceImpl]
  
  override val userService: UserService = wire[UserServiceImpl]
  override val fileExportService: FileExportService = wire[FileExportServiceImpl]
  override val updateService: UpdateService = wire[UpdateServiceImpl]
}
