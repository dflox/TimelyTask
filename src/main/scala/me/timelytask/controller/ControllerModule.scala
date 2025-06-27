package me.timelytask.controller

import com.softwaremill.macwire.wire
import me.timelytask.controller.commands.CommandHandler
import me.timelytask.controller.commands.handlerImpl.CommandHandlerImpl
import me.timelytask.controller.controllersImpl.{CoreControllerImpl, ModelControllerImpl, PersistenceControllerImpl, UpdateControllerImpl}
import me.timelytask.core.CoreModule
import me.timelytask.model.Model
import me.timelytask.serviceLayer.ServiceModule
import me.timelytask.serviceLayer.servicelayerImpl.ServiceModuleImpl
import me.timelytask.util.Publisher

trait ControllerModule(private val modelPublisher: Publisher[Model], private val coreModule: CoreModule) {
  private val serviceModule: ServiceModule = wire[ServiceModuleImpl]
  
  lazy val commandHandler: CommandHandler = wire[CommandHandlerImpl]

  lazy val modelController: ModelController = wire[ModelControllerImpl]

  lazy val coreController: CoreController = wire[CoreControllerImpl]

  lazy val persistenceController: PersistenceController = wire[PersistenceControllerImpl]
  
  lazy val updateController: UpdateController = wire[UpdateControllerImpl]
}

class ControllerModuleImpl(modelPublisher: Publisher[Model], coreModule: CoreModule) 
  extends ControllerModule(modelPublisher, coreModule)