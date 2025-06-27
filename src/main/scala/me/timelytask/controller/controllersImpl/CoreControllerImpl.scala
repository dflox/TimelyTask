package me.timelytask.controller.controllersImpl

import me.timelytask.controller.commands.*
import me.timelytask.controller.{CoreController, PersistenceController, UpdateController}
import me.timelytask.core.{CoreModule, StartUpConfig, UiInstanceConfig}
import me.timelytask.model.settings.CALENDAR
import me.timelytask.model.settings.UIType.{GUI, TUI}
import me.timelytask.view.UiInstance
import me.timelytask.view.views.MinimalStartUpView
import me.timelytask.view.views.viewImpl.gui.GUIMinimalStartupView
import me.timelytask.view.views.viewImpl.tui.TUIMinimalStartupView

class CoreControllerImpl(private val commandHandler: CommandHandler,
                         private val persistenceController: PersistenceController,
                         private val updateController: UpdateController,
                         private val coreModule: CoreModule)
  extends CoreController {

  private var uiInstances: Vector[UiInstance] = Vector.empty
  private var runningFlag: Boolean = false

  override def shutdownApplication(): Unit = {
    commandHandler.handle(new UndoableCommand[Unit](shutdownCommandHandler(), ()) {})
  }

  private def shutdownCommandHandler(): Handler[Unit] = (args: Unit) => {
    uiInstances.foreach(_.shutdown())
    runningFlag = false
    commandHandler.runner.cancel()
    true
  }

  override def startUpApplication(startUpConfig: Option[StartUpConfig]): Unit = {
    if (runningFlag) return
    updateController.init()

    createUiInstances(startUpConfig)

    uiInstances.foreach(_.run())
    runningFlag = true

    commandHandler.runner.await()
  }

  override def newGuiInstance(): Unit = newUiInstance(standardNewGuiInstanceConfig)

  override def closeInstance(uiInstance: UiInstance): Unit = {
    uiInstances = uiInstances.filterNot(_ == uiInstance)
    if (uiInstances.isEmpty) this.shutdownApplication()
    else uiInstance.shutdown()
  }

  private def createUiInstances(startUpConfig: Option[StartUpConfig]): Unit = {
    startUpConfig.getOrElse(throwException_StartUpFailed())
      .uiInstances.foreach(instanceConfig => newUiInstance(instanceConfig))
  }

  private def throwException_StartUpFailed(): Nothing = {
    throw new Exception("UI startup failed because the config is missing")
  }

  private def newUiInstance(uiInstanceConfig: UiInstanceConfig): Unit = injectUserIntoNewInstance(
    uiInstanceConfig,
    createUiInstance(_, uiInstanceConfig))

  private def createUiInstance(userToken: String, uiInstanceConfig: UiInstanceConfig): Unit = {
      persistenceController.provideModelFromDB(userToken)
      val newUiInstance = new UiInstance(uiInstanceConfig, coreModule, userToken)
      newUiInstance.run()
      uiInstances = uiInstances.appended(newUiInstance)
    }

  private val standardNewGuiInstanceConfig: UiInstanceConfig = UiInstanceConfig(List(GUI), CALENDAR)

  private def injectUserIntoNewInstance(instanceConfig: UiInstanceConfig,
                                         onInput: String => Unit)
  : Unit = {
    val startUpViews: Vector[MinimalStartUpView] = instanceConfig.uis.map {
      case GUI => new GUIMinimalStartupView
      case TUI => new TUIMinimalStartupView
    }.toVector
    startUpViews.foreach(_.render(inputStoppingCallbackWrapper(onInput, startUpViews, _)))
  }

  private def inputStoppingCallbackWrapper(onInput: String => Unit,
                                           vector: Vector[MinimalStartUpView],
                                           input: String)
  : Unit = {
    vector.foreach(m => m.kill())
    onInput(input)
  }
}
