package me.timelytask.controller.controllersImpl

import me.timelytask.controller.commands.*
import me.timelytask.controller.{CoreController, PersistenceController}
import me.timelytask.core.{CoreModule, StartUpConfig, UiInstanceConfig}
import me.timelytask.model.settings.CALENDAR
import me.timelytask.model.settings.UIType.GUI
import me.timelytask.view.UiInstance

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

  override def newInstance(): Unit = uiInstances = {
    val newUiInstance = new UiInstance(standardNewUiInstanceConfig, coreModule)
    newUiInstance.run()
    uiInstances.appended(newUiInstance)
  }

  override def closeInstance(uiInstance: UiInstance): Unit = {
    uiInstances = uiInstances.filterNot(_ == uiInstance)
    if(uiInstances.isEmpty) this.shutdownApplication()
    else uiInstance.shutdown()
  }

  private val standardNewUiInstanceConfig: UiInstanceConfig = UiInstanceConfig(List(GUI), CALENDAR)
}
