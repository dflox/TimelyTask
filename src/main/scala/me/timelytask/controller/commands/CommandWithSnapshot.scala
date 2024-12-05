package me.timelytask.controller.commands

class CommandWithSnapshot {
  def doStep(): Boolean = {
    true
  }

  def undoStep(): Boolean = {
    true
  }

  def getSnapshot(): Unit = {
    //
  }
}
