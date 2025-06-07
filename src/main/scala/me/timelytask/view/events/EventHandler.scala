package me.timelytask.view.events

import me.timelytask.util.CancelableFuture
import me.timelytask.view.events.event.Event

trait EventHandler {
  private[events] val runner: CancelableFuture[Unit]
  def handle(command: Event[?]): Unit
  def shutdown(): Unit
}
