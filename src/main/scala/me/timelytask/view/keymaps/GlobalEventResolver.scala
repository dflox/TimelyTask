package me.timelytask.view.keymaps

import me.timelytask.model.settings.EventTypeId
import me.timelytask.view.events.container.GlobalEventContainer

trait GlobalEventResolver {
  protected val eventContainer: GlobalEventContainer

  def resolveAndCallEvent(eventType: EventTypeId): Boolean
}

class GlobalEventResolverImpl( override protected val eventContainer: GlobalEventContainer) 
  extends GlobalEventResolver {


  override def resolveAndCallEvent(eventType: EventTypeId): Boolean = {
    given Conversion[Unit, Boolean]{def apply(unit: Unit): Boolean = true}
    eventType match {
      case EventTypeId("Undo") => eventContainer.undo()
      case EventTypeId("Redo") => eventContainer.redo()
      case EventTypeId("AddRandomTask") => eventContainer.addRandomTask()
      case EventTypeId("ShutdownApplication") => eventContainer.shutdownApplication()
      case EventTypeId("NewGuiWindow") => eventContainer.newWindow()
      case EventTypeId("NewInstance") => eventContainer.newInstance()
      case EventTypeId("CloseInstance") => eventContainer.closeInstance()
      case EventTypeId("NewTask") => eventContainer.newTask()
      case EventTypeId("SwitchToView") => eventContainer.switchToView()
      case _ => false
    }
  }
}
