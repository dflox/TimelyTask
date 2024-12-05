package me.timelytask.controller.commands

import me.timelytask.controller.commands.Command

class UndoManager {
  private var undoStack: List[Command[?]] = Nil
  private var redoStack: List[Command[?]] = Nil

  def doStep(command: Command[?]): Boolean = {
    command.doStep() match {
      case true =>
        undoStack = command :: undoStack
        redoStack = Nil
        true
      case false => false
    }
  }

  def undoStep(): Boolean = {
    undoStack match {
      case Nil => false
      case head :: stack =>
        head.undoStep() match {
          case true =>
            undoStack = stack
            redoStack = head :: redoStack
            true
          case false => false
        }
    }
  }

  def redoStep(): Boolean = {
    redoStack match {
      case Nil => false
      case head :: stack =>
        head.doStep() match {
          case true =>
            redoStack = stack
            undoStack = head :: undoStack
            true
          case false => false
        }
    }
  }
}