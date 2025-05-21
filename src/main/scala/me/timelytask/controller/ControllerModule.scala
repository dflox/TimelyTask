package me.timelytask.controller

import com.softwaremill.macwire.wire
import me.timelytask.controller.commands.{CommandHandler, CommandHandlerImpl}
import me.timelytask.core.CoreModule
import me.timelytask.model.Model
import me.timelytask.util.Publisher

trait ControllerModule(private val modelPublisher: Publisher[Model]) {

  lazy val commandHandler: CommandHandler = wire[CommandHandlerImpl]

  lazy val modelController: ModelController = wire[ModelControllerImpl]

  lazy val coreController: CoreController = wire[CoreControllerImpl]

  lazy val persistenceController: PersistenceController = wire[PersistenceControllerImpl]
}

class ControllerModuleImpl(modelPublisher: Publisher[Model]) extends ControllerModule(modelPublisher)