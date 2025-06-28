package me.timelytask.controller.commands

import me.timelytask.util.CancelableFuture

trait CommandHandler {
  private[controller] var runner: CancelableFuture[Unit]

  private[controller] def handle(command: Command[?]): Unit
  
  def undo(): Unit
  
  def redo(): Unit
}