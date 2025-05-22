package me.timelytask.controller

import me.timelytask.controller.commands.Command
import me.timelytask.core.StartUpConfig

trait CoreController {
  def shutdownApplication(): Unit
  def startUpApplication(startUpConfig: Option[StartUpConfig]): Unit
}
