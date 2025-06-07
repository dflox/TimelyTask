package me.timelytask.view

import com.softwaremill.macwire.wire
import me.timelytask.core.{CoreModule, UiInstanceConfig}
import me.timelytask.model.settings.UIType.{GUI, TUI}
import me.timelytask.model.settings.{CALENDAR, UIType, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.events.EventHandler
import me.timelytask.view.events.container.GlobalEventContainer
import me.timelytask.view.events.container.contailerImpl.GlobalEventContainerImpl
import me.timelytask.view.events.handler.EventHandlerImpl
import me.timelytask.view.views.UIManager
import me.timelytask.view.views.commonsModules.commonsModuleImpl.{CalendarCommonsModuleImpl, TaskEditCommonsModuleImpl}
import me.timelytask.view.views.commonsModules.{CalendarCommonsModule, TaskEditCommonsModule}
import me.timelytask.view.views.viewImpl.gui.GuiManager
import me.timelytask.view.views.viewImpl.tui.TUIManager

//TODO: create an interface for the UiInstance so the implementation is independent. Make sure 
// that only the interface is ever used anywhere.

class UiInstance(protected val uiInstanceConfig: UiInstanceConfig,
                 protected val coreModule: CoreModule) {

  private val self = this

  def shutdown(): Unit = {
    uiManager.foreach(_.shutdown())
    eventHandler.shutdown()
  }

  def addUi(uiType: UIType): Unit = {
    addUiManager(uiType)
  }
  
  private lazy val activeViewPublisher: Publisher[ViewType] = wire[PublisherImpl[ViewType]]
  private lazy val eventHandler: EventHandler = wire[EventHandlerImpl]

  private lazy val globalEventContainer: GlobalEventContainer = wire[GlobalEventContainerImpl]
  
  //CalendarView
  private lazy val calendarViewModule: CalendarCommonsModule = wire[CalendarCommonsModuleImpl]

  //TaskEditView
  private lazy val taskEditViewModule: TaskEditCommonsModule = wire[TaskEditCommonsModuleImpl]
  
  private var uiManager: Vector[UIManager[?]] = Vector.empty

  def run(): Unit = {
    init()
    uiInstanceConfig.uis.foreach(addUiManager)
  }

  private def addUiManager(uiType: UIType): Unit = {
    uiType match {
      case TUI => uiManager = {
        val tuiManager= wire[TUIManager]
        tuiManager.run()
        uiManager.appended(tuiManager)
      }
      case GUI => uiManager = {
        val guiManager = wire[GuiManager]
        guiManager.run()
        uiManager.appended(guiManager)
      }
      case null => throwUnknownManagerException(uiType)
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
