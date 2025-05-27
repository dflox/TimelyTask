package me.timelytask.view.eventHandlers

import me.timelytask.util.CancelableFuture
import me.timelytask.view.events.Event

trait EventHandler {
  private[eventHandlers] val runner: CancelableFuture[Unit]
  def handle(command: Event[?]): Unit
  def shutdown(): Unit
}
