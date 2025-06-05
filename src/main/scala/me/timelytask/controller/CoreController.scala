package me.timelytask.controller

import me.timelytask.UiInstance
import me.timelytask.core.StartUpConfig

trait CoreController {
  def shutdownApplication(): Unit
  def startUpApplication(startUpConfig: Option[StartUpConfig]): Unit
  def newInstance(): Unit
  def closeInstance(uiInstance: UiInstance): Unit
}
