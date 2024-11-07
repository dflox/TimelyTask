package me.timelytask.view.tui.dialog

import me.timelytask.view.tui.UtilTUI.createLine
import me.timelytask.view.viewmodel.dialogmodel.{DialogModel, TextInputDialogModel}
import org.jline.reader.impl.history.DefaultHistory
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.Terminal

class TextInputDialogTUI(val dialogModel: DialogModel, val terminal: Terminal) extends TUIDialog {
  
  def getUserInput: String = {
    val textInputDialogModel = dialogModel.asInstanceOf[TextInputDialogModel]
    val dialogString = createDialogString(textInputDialogModel.description, terminalWidth)
    val viewWithDialog = overlapString(textInputDialogModel.currentView, dialogString)
    terminal.writer().println(viewWithDialog)

    val history = new DefaultHistory()
    val reader: LineReader = LineReaderBuilder.builder()
      .terminal(terminal)
      .history(history) // Attach the history
      .variable(LineReader.HISTORY_SIZE, 0) // Disable history size
      .build()

    history.purge() // Clear the history before each input
    val input = reader.readLine("> ")
    history.purge() // Clear the history after input
    input
  }
  
  private def createDialogString(description: String, terminalWidth: Int): String = {
    val stringBuilder = new StringBuilder()
    stringBuilder.append(createLine(terminalWidth) + "\n")
    stringBuilder.append(description)
    stringBuilder.toString()
  }
}
