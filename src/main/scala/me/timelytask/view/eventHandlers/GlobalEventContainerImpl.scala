package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.CommandHandler
import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.util.Publisher
import me.timelytask.view.events.Event
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}

// TODO: Adapt this class to the new event handling system, add GlobalEventResolver and extend 
//  the Keymap to include the global events
class GlobalEventContainerImpl(calendarViewModelPublisher: Publisher[CalendarViewModel],
                               taskEditViewModelPublisher: Publisher[TaskEditViewModel],
                               modelPublisher: Publisher[Model],
                               undoManager: CommandHandler,
                               activeViewPublisher: Publisher[ViewType]) extends GlobalEventContainer {

  private val eventHandler: EventHandler = new EventHandlerImpl()

  override def undo(): Unit = {
    val event = new Event[Unit](
      (args: Unit) => {
        undoManager.undo()
        true
      },
      (args: Unit) => None,
      ()
    ){}
    eventHandler.handle(event)
  }

  override def redo(): Unit = {
    val event = new Event[Unit](
      (args: Unit) => {
        undoManager.redo()
        true
      },
      (args: Unit) => None,
      ()
    ) {}
    eventHandler.handle(event)
  }

  override def switchToView(viewType: ViewType): Unit = {
    val event = new Event[ViewType](
      (args: ViewType) => {
        activeViewPublisher.update(Some(args))
        true
      },
      (args: ViewType) => None,
      viewType
    ){}
    eventHandler.handle(event)
  }

  override def shutdown(): Unit = {
    eventHandler.shutdown()
  }

}
