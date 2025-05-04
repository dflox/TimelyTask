package me.timelytask.controller.commands

import java.util.concurrent.LinkedBlockingQueue

class CommandHandler(commandQueue: LinkedBlockingQueue[Command[?]]) {
  private var undoStack: List[Command[?]] = Nil
  private var redoStack: List[Command[?]] = Nil
  
  def handle(): Unit = {
    while(true){
      val nextCommand = commandQueue.take()
      this.doStep(nextCommand)
    }
  }
  
  private def doStep(command: Command[?]): Boolean = {
    if command.execute then
      undoStack = command :: undoStack
      redoStack = Nil
      true
    else
      false
  }

  private def undoStep(): Boolean = {
    undoStack match {
      case Nil => false
      case head :: stack =>
        if head.undo then
          undoStack = stack
          redoStack = head :: redoStack
          true
        else
          false
    }
  }

  private def redoStep(): Boolean = {
    redoStack match {
      case Nil => false
      case head :: stack =>
        if head.redo then
          redoStack = stack
          undoStack = head :: undoStack
          true
        else
          false
    }
  }
}