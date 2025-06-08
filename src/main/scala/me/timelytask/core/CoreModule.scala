package me.timelytask.core

import com.softwaremill.macwire.wire
import me.timelytask.controller.{ControllerModule, ControllerModuleImpl}
import me.timelytask.model.Model
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl

trait CoreModule{
  private lazy val self: CoreModule = this
  
  private lazy val modelPublisher: Publisher[Model] = wire[PublisherImpl[Model]]
  
  lazy val controllers: ControllerModule = wire[ControllerModuleImpl]
  
  def registerModelListener(listener: Option[Model] => Unit): Unit = modelPublisher.addListener(listener)
}

class CoreModuleImpl extends CoreModule
