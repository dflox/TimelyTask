package me.timelytask.controller.commands

import me.timelytask.util.CancelableFuture

trait CommandHandler {
  private[controller] val runner: CancelableFuture[Unit]

  private[controller] def handle(command: Command[?]): Unit
  
  def undo(): Unit
  
  def redo(): Unit
}