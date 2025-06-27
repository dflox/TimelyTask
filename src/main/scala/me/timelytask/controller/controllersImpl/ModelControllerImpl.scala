package me.timelytask.controller.controllersImpl

import me.timelytask.controller.ModelController
import me.timelytask.controller.commands.*
import me.timelytask.model.Model
import me.timelytask.model.task.Task
import me.timelytask.serviceLayer.ServiceModule
import me.timelytask.util.Publisher

class ModelControllerImpl(modelPublisher: Publisher[Model],
                          serviceModule: ServiceModule,
                          commandHandler: CommandHandler)
  extends Controller(modelPublisher, commandHandler)
  with ModelController {

  override def addTask(userToken: String, task: Task): Unit = commandHandler.handle(new 
      InversibleCommand[Task](
    new InversibleHandler[Task] {
      override def apply(args: Task): Boolean = {
        serviceModule.taskService.newTask(userToken, args)
        true
      }

      override def unapply(args: Task): Boolean = {
        serviceModule.taskService.deleteTask(userToken, args.uuid)
        true
      }
    },
    task
  ) {})

  override def removeTask(userToken: String, task: Task): Unit = commandHandler.handle(new 
      InversibleCommand[Task](
    new InversibleHandler[Task] {
      override def apply(args: Task): Boolean = {
        serviceModule.taskService.deleteTask(userToken, args.uuid)
        true
      }

      override def unapply(args: Task): Boolean = {
        serviceModule.taskService.newTask(userToken, args)
        true
      }
    },
    task
  ) {})
}
