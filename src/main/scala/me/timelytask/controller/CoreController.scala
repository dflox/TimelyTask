package me.timelytask.controller

import me.timelytask.core.StartUpConfig
import me.timelytask.view.UiInstance

trait CoreController {
  def shutdownApplication(): Unit
  def startUpApplication(startUpConfig: Option[StartUpConfig]): Unit
  def newInstance(): Unit
  def closeInstance(uiInstance: UiInstance): Unit
}
