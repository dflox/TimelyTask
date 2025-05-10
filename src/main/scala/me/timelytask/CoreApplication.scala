package me.timelytask

import me.timelytask.controller.commands.{Command, CommandHandler}
import me.timelytask.controller.{ModelController, PersistenceController}
import me.timelytask.core.{StartUpConfig, UiInstanceConfig}
import me.timelytask.model.Model
import me.timelytask.model.settings.UIType.TUI
import me.timelytask.util.FileIO
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.util.serialization.SerializationStrategy
import me.timelytask.util.serialization.decoder.given
import me.timelytask.util.serialization.encoder.given

import java.util.concurrent.LinkedBlockingQueue

class CoreApplication {
  private val modelPublisher: PublisherImpl[Model] = PublisherImpl[Model](Some(Model.default))

  private val taskController: ModelController = new ModelController(modelPublisher)

  private val persistenceController: PersistenceController = new PersistenceController(modelPublisher)

  private val commandQueue: LinkedBlockingQueue[Command[?]] = new LinkedBlockingQueue[Command[?]]()

  private val undoManager: CommandHandler = new CommandHandler(commandQueue)

  private var startUpConfig: Option[StartUpConfig] = None

  private val startUpFileFormat: String = "yaml"
  private val startUpConfigFilePath: String = "startUpConfig." + startUpFileFormat

  def run(): Unit = {
    if (!tryLoadStartUpConfig()) {
      resetStartUpConfig()
      tryLoadStartUpConfig()
    }
    validateSetup()

    // Initialize Controllers
    persistenceController.init()
    taskController.init()

    val uiInstances = startUpConfig
      .getOrElse(throwException_StartUpFailed())
      .uiInstances
      .map(ui => spawnUiInstance(ui))
  }

  private def tryLoadStartUpConfig(): Boolean = {
    FileIO.readFromFile(startUpConfigFilePath) match
      case Some(str) => startUpConfig = SerializationStrategy(startUpFileFormat)
        .deserialize[StartUpConfig](str)
        true
      case None => false
  }

  private def resetStartUpConfig(): Boolean = {
    val defaultStartUpConfig = StartUpConfig.default
    val configStr = SerializationStrategy(startUpFileFormat)
      .serialize[StartUpConfig](defaultStartUpConfig)
    FileIO.writeToFile(startUpConfigFilePath, configStr)
  }

  private def validateSetup(): Unit = {
    startUpConfig.getOrElse(throwException_StartUpConfigLoadingFailed()).validate()
  }
  
  private def throwException_StartUpFailed(): Nothing = {
    throw new Exception("UI startup failed because the config is missing")
  }
  
  private def throwException_StartUpConfigLoadingFailed(): Nothing = {
    throw new Exception("StartUpConfig could not be loaded!")
  }

  private def spawnUiInstance(uiInstanceConfig: UiInstanceConfig): UiInstance = {
    val uiInstance: UiInstance = new UiInstance(
      uiInstanceConfig,
      modelPublisher,
      undoManager,
      commandQueue)
    uiInstance
  }
}
