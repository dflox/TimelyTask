package me.timelytask.view.tui.dialog

import me.timelytask.view.tui.TuiUtils.createLine
import me.timelytask.view.viewmodel.dialogmodel.{ConfirmDialogModel, DialogModel}
import org.jline.terminal.Terminal

class ConfirmDialogTUI(val dialogModel: Option[ConfirmDialogModel],
                       val currentView: Option[String],
                       val terminal: Terminal)
  extends TUIDialog {
  
  override def getUserInput: Boolean = {
    if dialogModel.isEmpty | currentView.isEmpty then return false

    val dialogString = createDialogString(dialogModel.get.question, terminalWidth)
    val viewWithDialog = overlapString(currentView.get, dialogString)
    terminal.writer().println(viewWithDialog)

    var result = false
    var running = true
    while (running) {
      val input = terminal.reader().read().toChar.toString
      input match {
        case "y" => {
          result = true
          running = false
        }
        case "n" => {
          result = false
          running = false
        }
        case _ => {
          terminal.writer().println(createInvalidInputString(viewWithDialog, terminalWidth))
        }
      }
    }
    result
  }

  private def createDialogString(question: String, terminalWidth: Int): String = {
    val stringBuilder = new StringBuilder()
    stringBuilder.append(createLine(terminalWidth) + "\n")
    stringBuilder.append(question + "\n")
    stringBuilder.append("Please confirm ('y') or deny ('n')" + "\n")
    stringBuilder.append(createLine(terminalWidth))
    stringBuilder.toString()
  }

  private def createInvalidInputString(viewWithDialog: String, terminalWidth: Int): String = {
    val viewWithDialogLines = viewWithDialog.split("\n")
    viewWithDialogLines(
      viewWithDialogLines.length - 2) = "Invalid input. Please confirm ('y') or deny ('n')"
    viewWithDialogLines.mkString("\n")
  }


}
