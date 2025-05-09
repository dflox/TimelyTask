package me.timelytask

import me.timelytask.controller.commands.{Command, CommandHandler}
import me.timelytask.controller.{ModelController, PersistenceController}
import me.timelytask.core.StartUpConfig
import me.timelytask.model.Model
import me.timelytask.model.settings.UIType.TUI
import me.timelytask.util.serialization.SerializationStrategy
import me.timelytask.util.serialization.decoder.given
import me.timelytask.util.serialization.encoder.given
import me.timelytask.util.FileIO
import me.timelytask.util.publisher.PublisherImpl

import java.util.concurrent.LinkedBlockingQueue

class CoreApplication {
  val modelPublisher: PublisherImpl[Model] = PublisherImpl[Model](Some(Model.default))

  val taskController: ModelController = new ModelController(modelPublisher)

  val persistenceController: PersistenceController = new PersistenceController(modelPublisher)

  val commandQueue: LinkedBlockingQueue[Command[?]] = new LinkedBlockingQueue[Command[?]]()

  val undoManager: CommandHandler = new CommandHandler(commandQueue)

  var startUpConfig: Option[StartUpConfig] = None

  val startUpConfigFilePath: String = "startUpConfig.json"

  private def tryLoadStartUpConfig(): Boolean = {
    FileIO.readFromFile(startUpConfigFilePath) match
      case Some(str) => startUpConfig = SerializationStrategy("json").deserialize[StartUpConfig](
        startUpConfigFilePath)
        true
      case None => false
  }

  private def resetStartUpConfig(): Boolean = {
    val defaultStartUpConfig = StartUpConfig.default
    val jsonStr = SerializationStrategy("json")
      .serialize[StartUpConfig](defaultStartUpConfig)
    FileIO.writeToFile(startUpConfigFilePath, jsonStr)
  }

  private def validateSetup(): Unit = {
    val config = startUpConfig.getOrElse(throw new Exception("StartUpConfig could not be loaded!"))
    if(config.uiInstances
      .flatMap(i => i.uis.filter(ui => ui.eq(TUI)))
      .length > 1) throw new Exception("Only one TUI instance is allowed!")
  }

  def run(): Unit = {
    if(!tryLoadStartUpConfig()) {
      resetStartUpConfig()
      tryLoadStartUpConfig()
    }
    validateSetup()

    // Initialize Controllers
    persistenceController.init()
    taskController.init()

    val uiInstance: UiInstance = new UiInstance(modelPublisher, undoManager, commandQueue)
    uiInstance.run()
  }
}
