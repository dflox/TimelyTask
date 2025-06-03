package me.timelytask.view.views

import com.softwaremill.macwire.{wire, wireWith}
import me.timelytask.core.CoreModule
import me.timelytask.model.Model
import me.timelytask.model.settings.{CALENDAR, KeymapConfig, TASKEdit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.eventHandlers.*
import me.timelytask.view.keymaps.{CalendarEventResolver, EventResolver, TaskEditEventResolver}
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel, ViewModel}

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
    coreModule.registerModelUpdater(mapViewKeymapConfig(listener))

  protected def mapViewKeymapConfig(listener: KeymapConfig => Unit): Option[Model] => Unit
}