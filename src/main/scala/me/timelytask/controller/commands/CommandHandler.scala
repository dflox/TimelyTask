package me.timelytask.controller.commands

import me.timelytask.util.CancelableFuture

trait CommandHandler {
  private[controller] val runner: CancelableFuture[Unit]
  def handle(command: Command[?]): Unit
}