package me.timelytask.view.gui.dialog

import scalafx.scene.control.TextInputDialog
import scalafx.stage.Stage

class TextInputDialogGUI(stage: Stage, question: String) {

  def showAndWait(): Option[String] = {
    // Create and show text input dialog
    val dialog = new TextInputDialog(defaultValue = "Input") {
      initOwner(stage)
      title = "Text Input Dialog"
      headerText = question
      contentText = "enter text:"
    }

    val result = dialog.showAndWait()

    // Return user's input or None if dialog was canceled
    result match {
      case Some(name) => Some(name)
      case None       => None
    }
  }
}