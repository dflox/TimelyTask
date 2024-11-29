package me.timelytask.controller.mediator

trait Mediator {
  def notify(sender: Any, event: String): Unit
}
