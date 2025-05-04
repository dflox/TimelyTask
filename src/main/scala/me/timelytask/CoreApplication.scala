package me.timelytask

import me.timelytask.controller.commands.{Command, CommandHandler}
import me.timelytask.controller.{ModelController, PersistenceController}
import me.timelytask.model.Model
import me.timelytask.util.Publisher

import java.util.concurrent.LinkedBlockingQueue

class CoreApplication {
  val modelPublisher: Publisher[Model] = Publisher[Model](Some(Model.default))
  
  val taskController: ModelController = new ModelController(modelPublisher)

  val persistenceController: PersistenceController = new PersistenceController(modelPublisher)
  
  val commandQueue: LinkedBlockingQueue[Command[?]] = new LinkedBlockingQueue[Command[?]]()

  val undoManager: CommandHandler = new CommandHandler(commandQueue)
  
  def validateSetup() = {
    
  }

  def run(): Unit = {
    // Load the model from the persistence layer
    // specify ui intances as liked
    
    // Initialize Controllers
    persistenceController.init()
    taskController.init()
    
    val uiInstance: UiInstance = new UiInstance(modelPublisher, undoManager, commandQueue)
    uiInstance.run()
  }
}
