package me.timelytask.controller.commands

import me.timelytask.util.CancelableFuture

trait CommandHandler {
  private[controller] var runner: CancelableFuture[Unit]

  private[controller] def handle(userToken: String, command: Command[?]): Unit
  
  private[controller] def handleGlobally(command: Command[?]): Unit
  
  private[controller] def terminateUserSession(userToken: String): Unit
  
  def undo(userToken: String): Unit
  
  def redo(userToken: String): Unit
}