package me.timelytask.controller.commands

import me.timelytask.controller.commands.Command

class UndoManager {
  private var undoStack: List[Command[?]] = Nil
  private var redoStack: List[Command[?]] = Nil

  def doStep(command: Command[?]): Boolean = {
    if command.doStep then
      undoStack = command :: undoStack
      redoStack = Nil
      true
    else
      false
  }

  def undoStep(): Boolean = {
    undoStack match {
      case Nil => false
      case head :: stack =>
        if head.undoStep then
          undoStack = stack
          redoStack = head :: redoStack
          true
        else
          false
    }
  }

  def redoStep(): Boolean = {
    redoStack match {
      case Nil => false
      case head :: stack =>
        if head.doStep then
          redoStack = stack
          undoStack = head :: undoStack
          true
        else
          false
    }
  }
}