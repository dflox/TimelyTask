package me.timelytask.controller

import me.timelytask.controller.commands.Command

trait CoreController {
  def shutdownApplication: Command[Unit]
}
