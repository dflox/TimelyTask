package me.timelytask.view.gui.dialog

import scalafx.scene.control.ChoiceDialog
import scalafx.stage.Stage

class ChoiceDialogGUI[T](stage: Stage, defaultChoice: T, choices: Seq[T], question: String, contentText: String) {

  def showAndWait(): Option[T] = {
    // Create and show choice dialog
    val dialog = new ChoiceDialog(defaultChoice, choices) {
      initOwner(stage)
      title = "Choice Dialog"
      headerText = question
      contentText = "select:"
    }

    val result = dialog.showAndWait()

    // Return user's choice or None if dialog was canceled
    result match {
      case Some(choice) => Some(choice)
      case None         => None
    }
  }
}