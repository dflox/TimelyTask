package me.timelytask.core

import com.softwaremill.macwire.wire
import me.timelytask.controller.{ControllerModule, ControllerModuleImpl}
import me.timelytask.model.Model
import me.timelytask.serviceLayer.ServiceModule
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl

trait CoreModule(serviceModule: ServiceModule) {
  private lazy val self: CoreModule = this

  private[core] lazy val modelPublisher: Publisher[Model] = wire[PublisherImpl[Model]]

  lazy val controllers: ControllerModule = wire[ControllerModuleImpl]
  
  def registerModelListener(listener: Option[Model] => Unit, userToken: String): Unit = modelPublisher
    .addListener(listener = listener, target = Some(userToken))
  
  def removeUserSession(userName: String): Unit = {
    modelPublisher.removeTarget(userName)
  }
}

class CoreModuleImpl(serviceModule: ServiceModule) extends CoreModule(serviceModule)
