package me.timelytask.controller

import me.timelytask.UiInstance
import me.timelytask.controller.commands.*
import me.timelytask.core.{CoreModule, StartUpConfig}

class CoreControllerImpl(private val commandHandler: CommandHandler,
                         private val persistenceController: PersistenceController,
                         private val coreModule: CoreModule)
  extends CoreController {

  private var uiInstances: Vector[UiInstance] = Vector.empty
  private var runningFlag: Boolean = false

  override def shutdownApplication(): Unit = {
    commandHandler.handle(new UndoableCommand[Unit](shutdownHandler(), ()) {})
  }

  private def shutdownHandler(): Handler[Unit] = (args: Unit) => {
    uiInstances.foreach(_.shutdown())
    runningFlag = false
    commandHandler.runner.cancel()
    true
  }

  override def startUpApplication(startUpConfig: Option[StartUpConfig]): Unit = {
    if (runningFlag) return
    persistenceController.init()
    
    createUiInstances(startUpConfig)
    uiInstances.foreach(_.run())
    runningFlag = true
    
    persistenceController.loadModelFromDB()
    
    commandHandler.runner.await()
  }

  private def createUiInstances(startUpConfig: Option[StartUpConfig]): Unit = {
    uiInstances = startUpConfig
      .getOrElse(throwException_StartUpFailed())
      .uiInstances.toVector
      .map(instanceConfig => new UiInstance(instanceConfig, coreModule))
  }

  private def throwException_StartUpFailed(): Nothing = {
    throw new Exception("UI startup failed because the config is missing")
  }
}
