package me.timelytask

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.core.validators.{GuiValidationStep, StartUpValidator, StartUpValidatorImpl, TuiValidationStep, ValidationStep}
import me.timelytask.core.{CoreModule, CoreModuleImpl, StartUpConfig}
import me.timelytask.util.FileIO
import me.timelytask.util.serialization.SerializationStrategy
import me.timelytask.util.serialization.decoder.given
import me.timelytask.util.serialization.encoder.given

class ApplicationCore {
  
  private lazy val coreModule: CoreModule = wire[CoreModuleImpl]
  
  private var startUpConfig: Option[StartUpConfig] = None
  
  private val startUpValidator: StartUpValidator = wire[StartUpValidatorImpl]

  private val startUpFileFormat: String = "yaml"
  private val startUpConfigFilePath: String = "startUpConfig." + startUpFileFormat

  def run(): Unit = {
    if (!tryLoadStartUpConfig()) {
      resetStartUpConfig()
      tryLoadStartUpConfig()
    }
    startUpValidator.validate(startUpConfig)
    
    coreModule.controllers.coreController.startUpApplication(startUpConfig)
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
}
