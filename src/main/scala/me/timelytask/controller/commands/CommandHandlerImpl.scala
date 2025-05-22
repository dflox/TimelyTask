package me.timelytask.controller.commands

import me.timelytask.util.CancelableFuture

import java.util.concurrent.LinkedBlockingQueue

class CommandHandlerImpl extends CommandHandler {
  private val commandQueue: LinkedBlockingQueue[Command[?]] = new LinkedBlockingQueue[Command[?]]()
  private var undoStack: List[Command[?]] = Nil
  private var redoStack: List[Command[?]] = Nil
  override private[controller] val runner: CancelableFuture[Unit] =
    CancelableFuture[Unit](commandExecutor())
  
  private def commandExecutor(): Unit = {
    while (true) {
      val nextCommand = commandQueue.take()
      this.doStep(nextCommand)
    }
  }
  
  override def handle(command: Command[?]): Unit = {
    commandQueue.add(command)
  }

  private def doStep(command: Command[?]): Boolean = {
    if command.execute then
      command match {
        case cmd: UndoableCommand[?] => undoStack = Nil
        case _ => undoStack = command :: undoStack
      }
      redoStack = Nil
      true
    else
      false
  }

  //TODO: Write Methods to create Undo and Redo Commands.
  
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