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
) {
  lazy val viewModelPublisher: Publisher[ViewModelType] = wire[PublisherImpl[ViewModelType]]
  lazy val eventContainer: EventContainer[VT, ViewModelType]
  lazy val eventResolver: EventResolver[VT, ViewModelType]
  def registerKeymapUpdater(listener: KeymapConfig => Unit): Unit =
    coreModule.registerModelUpdater(mapKeymapConfig(listener))
  protected def mapKeymapConfig(listener: KeymapConfig => Unit): Option[Model] => Unit
}

trait CalendarCommonsModule extends ViewTypeCommonsModule[CALENDAR, CalendarViewModel]{
  override lazy val eventContainer: CalendarEventContainer = wireWith[CalendarEventContainerImpl](
    () => CalendarEventContainerImpl(viewModelPublisher,activeViewPublisher, coreModule))
  override lazy val eventResolver: EventResolver[CALENDAR, CalendarViewModel] = 
    wire[CalendarEventResolver]

  override def mapKeymapConfig(listener: KeymapConfig => Unit): Option[Model] => Unit =
    model => model.map(m => listener(m.config.keymaps(CALENDAR)))
}

class CalendarCommonsModuleImpl(coreModule: CoreModule,
                                activeViewPublisher: Publisher[ViewType])
  extends CalendarCommonsModule with ViewTypeCommonsModule[CALENDAR, CalendarViewModel](coreModule, activeViewPublisher)

trait TaskEditCommonsModule extends ViewTypeCommonsModule[TASKEdit, TaskEditViewModel] {
  override lazy val eventContainer: TaskEditEventContainer = wireWith[TaskEditEventContainerImpl](
    () => TaskEditEventContainerImpl(viewModelPublisher,activeViewPublisher, coreModule))
  override lazy val eventResolver: EventResolver[TASKEdit, TaskEditViewModel] = 
    wire[TaskEditEventResolver]
    
  override def mapKeymapConfig(listener: KeymapConfig => Unit): Option[Model] => Unit =
    model => model.map(m => listener(m.config.keymaps(TASKEdit)))
}

class TaskEditCommonsModuleImpl(coreModule: CoreModule,
                                 activeViewPublisher: Publisher[ViewType])
  extends TaskEditCommonsModule with ViewTypeCommonsModule[TASKEdit, TaskEditViewModel](coreModule, activeViewPublisher)