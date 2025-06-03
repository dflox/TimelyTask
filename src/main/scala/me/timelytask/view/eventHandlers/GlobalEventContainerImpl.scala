package me.timelytask.view.eventHandlers

import me.timelytask.core.CoreModule
import me.timelytask.model.Task
import me.timelytask.model.settings.{TABLE, TASKEdit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.events.Event
import org.joda.time.DateTime

import scala.util.Random

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

  override def switchToView(): Unit = {
    val event = new Event[Unit](
      _ => {
        if(activeViewPublisher.getValue.contains(TASKEdit)) false
        else {
          activeViewPublisher.update(Some(TABLE))
          true
        }
      },
      _ => None,
      ()
    ){}
    eventHandler.handle(event)
  }

  override def shutdownApplication(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => {
      coreModule.controllers.coreController.shutdownApplication()
      true
    },
    (args: Unit) => None,
    ()
  ){})

  override def newWindow(): Unit = ???

  override def newInstance(): Unit = ???

  override def addRandomTask(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => {
      coreModule.controllers.modelController.addTask(randomTask())
      true
    },
    (args: Unit) => None,
    ()
  ){})

  private def randomTask(): Task = {
    new Task(
      "Random Task",
      "Random Task Description",
      scheduleDate = DateTime.now()
        .withTime(8, 0, 0, 0)
        .plusMinutes(Random.nextInt(60*10))// random time today
    )
  }

  override def newTask(): Unit = ()  //TODO
}
