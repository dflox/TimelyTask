package me.timelytask.serviceLayer

trait ServiceModule() {
  
  val taskService: TaskService
  val configService: ConfigService
  val priorityService: PriorityService
  val tagService: TagService
  val taskStateService: TaskStateService

  val updateService: UpdateService
  val userService: UserService
  val modelService: ModelService
  val fileExportService: FileExportService
}
