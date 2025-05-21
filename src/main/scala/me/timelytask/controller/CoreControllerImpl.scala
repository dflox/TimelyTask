package me.timelytask.controller

import com.softwaremill.macwire.wire
import me.timelytask.UiInstance
import me.timelytask.controller.commands.{Command, CommandHandler, CommandHandlerImpl}

class CoreControllerImpl extends CoreController {

  private var uiInstances: Vector[UiInstance] = Vector.empty

  override def shutdownApplication: Command[Unit] = new Command[Unit] {
    override def execute: Boolean = {
      uiInstances.foreach(_.shutdown())
      true
    }
    override def undo: Boolean = {
      false
    }
    override def redo: Boolean = {
      false
    }
  }
}
