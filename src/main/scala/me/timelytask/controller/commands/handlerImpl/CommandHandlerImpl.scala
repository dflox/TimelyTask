package me.timelytask.controller.commands.handlerImpl

import me.timelytask.controller.commands.{Command, CommandHandler, Handler, UndoableCommand}
import me.timelytask.util.CancelableFuture

import java.util.concurrent.LinkedBlockingQueue

class CommandHandlerImpl extends CommandHandler {
  private val commandQueue: LinkedBlockingQueue[Command[?]] = new LinkedBlockingQueue[Command[?]]()
  private var undoStack: List[Command[?]] = Nil
  private var redoStack: List[Command[?]] = Nil

  private[controller] var runner: CancelableFuture[Unit] = CancelableFuture[Unit](commandExecutor
    (), onFailure = Some(handleFailure))

  private def handleFailure(throwable: Throwable): Unit = {
    System.err.println(throwable.toString)
    runner = CancelableFuture[Unit](commandExecutor(), onFailure = Some(handleFailure))
  }

  private def commandExecutor(): Unit = {
    while (true) {
      this.doStep(commandQueue.take())
    }
  }
  
  override def handle(command: Command[?]): Unit = commandQueue.add(command)

  private def doStep(command: Command[?]): Boolean = {
    if command.execute then
      command match {
        case cmd: CommandHandlerCommand => ()
        case cmd: UndoableCommand[?] =>
          undoStack = Nil
          redoStack = Nil
        case _ =>
          undoStack = command :: undoStack
          redoStack = Nil
      }
      true
    else
      false
  }
  
  private def undoStep(args: Unit): Boolean = {
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

  private def redoStep(args: Unit): Boolean = {
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

  override def undo(): Unit = commandQueue.add(new CommandHandlerCommand(undoStep) {})

  override def redo(): Unit = commandQueue.add(new CommandHandlerCommand(redoStep) {})

  private trait CommandHandlerCommand(handler: Handler[Unit]) extends Command[Unit] {
    private var done: Boolean = false

    override def execute: Boolean = {
      if (!done) {
        done = handler.apply(())
        done
      } else false
    }

    override def undo: Boolean = false

    override def redo: Boolean = false
  }
}