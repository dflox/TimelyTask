package me.timelytask.controller.commands

trait CommandHandler {

  def handle(command: Command[?]): Unit
}