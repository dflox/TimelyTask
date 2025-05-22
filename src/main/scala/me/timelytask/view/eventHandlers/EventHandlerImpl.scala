package me.timelytask.view.eventHandlers

import me.timelytask.util.CancelableFuture
import me.timelytask.view.events.Event

import java.util.concurrent.LinkedBlockingQueue

//TODO: Ensure on shutdown of UIInstance: 1. runner is cancelled 2. eventQueue is empty -> await 
// for runner to finish cleaning the event queue 
class EventHandlerImpl extends EventHandler {
  private val eventQueue: LinkedBlockingQueue[Event[?]] = new LinkedBlockingQueue[Event[?]]()
  private var running: Boolean = true
  override private[eventHandlers] val runner: CancelableFuture[Unit] = 
    CancelableFuture[Unit](commandExecutor())

  private def commandExecutor(): Unit = {
    while (running) {
      eventQueue.take().call
    }
  }
  
  override def handle(event: Event[?]): Unit = eventQueue.add(event)

  override def shutdown(): Unit = {
    running = false
    runner.await()
    while (!eventQueue.isEmpty) {
      eventQueue.take().call
    }
  }
}
