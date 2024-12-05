package me.timelytask.controller.commands

class UndoManager {
  private var undoStack: List[Command[_]] = Nil
  private var redoStack: List[Command[_]] = Nil

  def doStep(command: Command[_]): Boolean = {
    command match {
      case cmd: CommandWithSnapshot =>
        if (cmd.doStep()) {
          undoStack = cmd :: undoStack
          redoStack = Nil
          true
        } else {
          false
        }
      case cmd: CommandNormal =>
        if (cmd.doStep()) {
          undoStack = cmd :: undoStack
          redoStack = Nil
          true
        } else {
          false
        }
      case _ => false
    }
  }

  def undoStep(): Boolean = {
    undoStack match {
      case Nil => false
      case head :: stack =>
        head match {
          case cmd: CommandWithSnapshot =>
            if (cmd.undoStep()) {
              undoStack = stack
              redoStack = cmd :: redoStack
              true
            } else {
              false
            }
          case cmd: CommandNormal =>
            if (cmd.undoStep()) {
              undoStack = stack
              redoStack = cmd :: redoStack
              true
            } else {
              false
            }
          case _ => false
        }
    }
  }

  def redoStep(): Boolean = {
    redoStack match {
      case Nil => false
      case head :: stack =>
        head match {
          case cmd: CommandWithSnapshot =>
            if (cmd.doStep()) {
              redoStack = stack
              undoStack = cmd :: undoStack
              true
            } else {
              false
            }
          case cmd: CommandNormal =>
            if (cmd.doStep()) {
              redoStack = stack
              undoStack = cmd :: undoStack
              true
            } else {
              false
            }
          case _ => false
        }
    }
  }
}