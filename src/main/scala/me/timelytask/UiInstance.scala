package me.timelytask

import com.softwaremill.macwire.wire
import me.timelytask.core.{CoreModule, UiInstanceConfig}
import me.timelytask.model.settings.UIType.TUI
import me.timelytask.model.settings.{CALENDAR, UIType, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.UIManager
import me.timelytask.view.eventHandlers.{EventHandler, EventHandlerImpl}
import me.timelytask.view.tui.TUIManager
import me.timelytask.view.views.{CalendarCommonsModule, CalendarCommonsModuleImpl, TaskEditCommonsModule, TaskEditCommonsModuleImpl}

//TODO: create an interface for the UiInstance so the implementation is independent. Make sure 
// that only the interface is ever used anywhere.

class UiInstance(private val uiInstanceConfig: UiInstanceConfig,
                 private val coreModule: CoreModule) {

  def shutdown(): Unit = {
    uiManager.foreach(_.shutdown())
    eventHandler.shutdown()
  }
  
  val activeViewPublisher: Publisher[ViewType] = wire[PublisherImpl[ViewType]]
  val eventHandler: EventHandler = wire[EventHandlerImpl]
  //val globalEventHandler: GlobalEventHandler = wire[GlobalEventHandler]
  
  //CalendarView
  private lazy val calendarViewModule: CalendarCommonsModule = wire[CalendarCommonsModuleImpl]

  //TaskEditView
  private lazy val taskEditViewModule: TaskEditCommonsModule = wire[TaskEditCommonsModuleImpl]
  
  private var uiManager: Vector[UIManager[?]] = Vector.empty

  def run(): Unit = {
    init()
    uiInstanceConfig.uis.foreach(addUiManager)
    uiManager.foreach(_.run())
  }

  private def addUiManager(uiType: UIType): Unit = {
    uiType match {
      case TUI => uiManager = uiManager.appended(wire[TUIManager])
      case _ =>  throwUnknownManagerException(uiType)
    }
  }
  
  private def throwUnknownManagerException(UIType: UIType): Unit = {
    throw new Exception(s"For UIType $UIType is no UIManager configured.")
  } 
  
  private def init(): Unit = {
    // Initialize the event containers
    activeViewPublisher.update(Some(uiInstanceConfig.startView))
    calendarViewModule.eventContainer.init()
    taskEditViewModule.eventContainer.init()
  }
}
