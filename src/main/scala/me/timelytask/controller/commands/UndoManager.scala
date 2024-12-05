package me.timelytask.controller.commands

class UndoManager {
  private var undoStack: List[Command[_]] = Nil
  private var redoStack: List[Command[_]] = Nil

  def doStep(command: Command[_]): Boolean = {
    if (command.doStep()) {
      undoStack = command :: undoStack
      redoStack = Nil
      true
    } else {
      false
    }
  }

  def undoStep(): Boolean = {
    undoStack match {
      case Nil => false
      case head :: stack =>
        if (head.undoStep) {
          undoStack = stack
          redoStack = head :: redoStack
          true
        } else {
          false
        }
    }
  }

  def redoStep(): Boolean = {
    redoStack match {
      case Nil => false
      case head :: stack =>
        if (head.doStep()) {
          redoStack = stack
          undoStack = head :: undoStack
          true
        } else {
          false
        }
    }
  }
}