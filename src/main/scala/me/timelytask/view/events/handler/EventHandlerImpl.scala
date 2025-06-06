package me.timelytask.view.events.handler

import me.timelytask.util.CancelableFuture
import me.timelytask.view.events.EventHandler
import me.timelytask.view.events.event.Event

import java.util.concurrent.LinkedBlockingQueue
import scala.concurrent.duration.{Duration, MILLISECONDS, SECONDS}

class EventHandlerImpl extends EventHandler {
  private val eventQueue: LinkedBlockingQueue[Event[?]] = new LinkedBlockingQueue[Event[?]]()
  private var running: Boolean = true
  override private[events] val runner: CancelableFuture[Unit] = 
    CancelableFuture[Unit](commandExecutor())

  private def commandExecutor(): Unit = {
    while (running) {
      eventQueue.take().call
    }
  }
  
  override def handle(event: Event[?]): Unit = eventQueue.add(event)

  override def shutdown(): Unit = {
    running = false
    runner.await(Duration(200, MILLISECONDS))
    while (!eventQueue.isEmpty) {
      eventQueue.take().call
    }
  }
}
