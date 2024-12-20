package me.timelytask.view.gui.dialog

import scalafx.scene.control.{Alert, ButtonType}
import scalafx.stage.Stage

class ConfirmDialogGUI(stage: Stage, question: String) {

  def showAndWait(): Boolean = {
    // Create and show confirmation alert
    val alert = new Alert(Alert.AlertType.Confirmation) {
      initOwner(stage)
      title = "Confirmation Dialog"
      headerText = question
      contentText = "confirm?"
    }

    val result = alert.showAndWait()

    // React to user's selection
    result match {
      case Some(ButtonType.OK) => true
      case _                   => false
    }
  }
}