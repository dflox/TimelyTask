package me.timelytask.view.gui.dialog

import scalafx.scene.control.TextInputDialog
import scalafx.stage.Stage
import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, DateTimeException}

class DateInputDialogGUI(stage: Stage, question: String) {

  def showAndWait(): Option[LocalDateTime] = {
    // Create and show text input dialog
    val dialog = new TextInputDialog(defaultValue = "yyyy-MM-dd HH:mm") {
      initOwner(stage)
      title = "Date Input Dialog"
      headerText = question
      contentText = "Enter date and time (yyyy-MM-dd HH:mm):"
    }

    val result = dialog.showAndWait()

    // Validate and return user's input as LocalDateTime or None if invalid
    result match {
      case Some(input) =>
        try {
          val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
          Some(LocalDateTime.parse(input, formatter))
        } catch {
          case _: DateTimeException => None
        }
      case None => None
    }
  }
}