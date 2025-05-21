package me.timelytask

import com.softwaremill.macwire.wire
import me.timelytask.controller.*
import me.timelytask.controller.commands.{Command, CommandHandler, CommandHandlerImpl}
import me.timelytask.core.{CoreModule, CoreModuleImpl, StartUpConfig, UiInstanceConfig}
import me.timelytask.model.Model
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.util.serialization.SerializationStrategy
import me.timelytask.util.serialization.decoder.given
import me.timelytask.util.serialization.encoder.given
import me.timelytask.util.{FileIO, Publisher}

import java.util.concurrent.LinkedBlockingQueue

class ApplicationCore {
  
  private val coreModule: CoreModule = wire[CoreModuleImpl]
  
  private var startUpConfig: Option[StartUpConfig] = None

  private val startUpFileFormat: String = "yaml"
  private val startUpConfigFilePath: String = "startUpConfig." + startUpFileFormat
  
  private var uiInstances: Vector[UiInstance] = Vector.empty 

  def run(): Unit = {
    if (!tryLoadStartUpConfig()) {
      resetStartUpConfig()
      tryLoadStartUpConfig()
    }
    validateSetup()
    
    //TODO: move instance creation to the CoreController.
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
  
  private def throwException_StartUpConfigLoadingFailed(): Nothing = {
    throw new Exception("StartUpConfig could not be loaded!")
  }

  private def throwException_StartUpFailed(): Nothing = {
    throw new Exception("UI startup failed because the config is missing")
  }
  
  private def spawnUiInstance(uiInstanceConfig: UiInstanceConfig): UiInstance = {
    new UiInstance(uiInstanceConfig, coreModule)
  }
}
