package me.timelytask.view.eventHandlers

import me.timelytask.core.CoreModule
import me.timelytask.model.settings.ViewType
import me.timelytask.util.Publisher
import me.timelytask.view.events.Event

// TODO: add GlobalEventResolver and extend the Keymap to include the global events
class GlobalEventContainerImpl(coreModule: CoreModule,
                               eventHandler: EventHandler,
                               activeViewPublisher: Publisher[ViewType]) extends GlobalEventContainer {

  override def undo(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => {
      coreModule.controllers.commandHandler.undo()
      true
    },
    (args: Unit) => None,
    ()
  ) {})

  override def redo(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => {
      coreModule.controllers.commandHandler.redo()
      true
    },
    (args: Unit) => None,
    ()
  ) {})

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
