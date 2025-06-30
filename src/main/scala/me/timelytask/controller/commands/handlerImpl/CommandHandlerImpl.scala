package me.timelytask.controller.commands.handlerImpl

import me.timelytask.controller.commands.{Command, CommandHandler, Handler, IrreversibleCommand}
import me.timelytask.util.CancelableFuture

import java.util.concurrent.LinkedBlockingQueue

class CommandHandlerImpl extends CommandHandler {
  private case class QueueCommand(userToken: String, command: Command[?])

  private case class UserSession(undoStack: List[Command[?]], redoStack: List[Command[?]])

  private val commandQueue: LinkedBlockingQueue[QueueCommand] = new
      LinkedBlockingQueue[QueueCommand]()

  private var userSessions: Map[String, UserSession] = Map.empty

  private[controller] var runner: CancelableFuture[Unit] = CancelableFuture[Unit](
    commandExecutor(), onFailure = Some(handleFailure))

  private def handleFailure(throwable: Throwable): Unit = {
    System.err.println(throwable.toString)
    runner = CancelableFuture[Unit](commandExecutor(), onFailure = Some(handleFailure))
  }

  private def commandExecutor(): Unit = {
    while (true) {
      this.doStep(commandQueue.take())
    }
  }

  private[controller] override def handle(userToken: String, command: Command[?]): Unit =
    commandQueue.add(QueueCommand(
    userToken, command))

  private def doStep(queueCommand: QueueCommand): Boolean = {
    if queueCommand.command.execute then
      queueCommand.command match {
        case cmd: CommandHandlerCommand => ()
        case cmd: IrreversibleCommand[?] =>
          userSessions = userSessions.removed(queueCommand.userToken)
        case _ =>
          val userSession = userSessions.getOrElse(queueCommand.userToken, UserSession(Nil, Nil))
          userSessions = userSessions.updated(queueCommand.userToken,
            userSession.copy(
              undoStack = queueCommand.command :: userSession.undoStack,
              redoStack = Nil
            ))
      }
      true
    else
      false
  }

  override def undo(userToken: String): Unit = commandQueue.add(QueueCommand(userToken, new
      CommandHandlerCommand(undoStep(_, userToken)) {}))

  override def redo(userToken: String): Unit = commandQueue.add(QueueCommand(userToken, new
      CommandHandlerCommand(redoStep(_, userToken)) {}))

  private def undoStep(args: Unit, userToken: String): Boolean = {
    userSessions.get(userToken) match {
      case Some(session) => session.undoStack match {
        case Nil => false
        case head :: stack =>
          if head.undo then
            userSessions = userSessions.updated(userToken,
              session.copy(undoStack = stack, redoStack = head :: session.redoStack))
            true
          else
            false
      }
      case None => false
    }
  }

  private def redoStep(args: Unit, userToken: String): Boolean = {
    userSessions.get(userToken) match {
      case Some(session) => session.redoStack match {
        case Nil => false
        case head :: stack =>
          if head.redo then
            userSessions = userSessions.updated(userToken,
              session.copy(undoStack = head :: session.undoStack, redoStack = stack))
            true
          else
            false
      }
      case None => false
    }
  }

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

  override private[controller] def terminateUserSession(userToken: String): Unit = {
    userSessions = userSessions.removed(userToken)
  }

  override private[controller] def handleGlobally(command: Command[?]): Unit =
    commandQueue.add(QueueCommand("", command))
}