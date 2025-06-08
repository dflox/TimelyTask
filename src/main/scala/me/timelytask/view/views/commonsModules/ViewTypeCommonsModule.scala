package me.timelytask.view.views.commonsModules

import com.softwaremill.macwire.wire
import me.timelytask.core.CoreModule
import me.timelytask.model.Model
import me.timelytask.model.settings.{KeymapConfig, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.events.container.GlobalEventContainer
import me.timelytask.view.events.{EventContainer, EventHandler}
import me.timelytask.view.keymaps.EventResolver
import me.timelytask.view.viewmodel.ViewModel

trait ViewTypeCommonsModule[VT <: ViewType, ViewModelType <: ViewModel[VT, ViewModelType]]
(
  protected val coreModule: CoreModule,
  val activeViewPublisher: Publisher[ViewType],
  protected val eventHandler: EventHandler,
  val globalEventContainer: GlobalEventContainer
) {
  lazy val viewModelPublisher: Publisher[ViewModelType] = wire[PublisherImpl[ViewModelType]]
  
  lazy val eventContainer: EventContainer[VT, ViewModelType]

  lazy val eventResolver: EventResolver[VT, ViewModelType]

  def registerKeymapUpdater(listener: KeymapConfig => Unit): Unit =
    coreModule.registerModelListener(mapViewKeymapConfig(listener))

  protected def mapViewKeymapConfig(listener: KeymapConfig => Unit): Option[Model] => Unit
}