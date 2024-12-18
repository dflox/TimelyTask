package me.timelytask.view.tui.dialog

import me.timelytask.view.tui.TuiUtils.createLine
import me.timelytask.view.viewmodel.dialogmodel.{DialogModel, InputDialogModel}
import org.jline.reader.impl.history.DefaultHistory
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.Terminal

class TextInputDialogTUI(val dialogModel: Option[InputDialogModel[String]],
                          val currentView: Option[String],
                         val terminal: Terminal) extends TUIDialog {

  def getUserInput: Option[String] = {
    if dialogModel.isEmpty | currentView.isEmpty then return None

    val dialogString = createDialogString(dialogModel.get.description, terminalWidth)
    val viewWithDialog = overlapString(currentView.get, dialogString)
    terminal.writer().println(viewWithDialog)

    val history = new DefaultHistory()
    val reader: LineReader = LineReaderBuilder.builder()
      .terminal(terminal)
      .history(history) // Attach the history
      .variable(LineReader.HISTORY_SIZE, 0) // Disable history size
      .build()

    history.purge() // Clear the history before each input
    val input = reader.readLine("> ", null, dialogModel.get.default.getOrElse(""))
    history.purge() // Clear the history after input

    Some(input)
  }

  private def createDialogString(description: String, terminalWidth: Int): String = {
    val stringBuilder = new StringBuilder()
    stringBuilder.append(createLine(terminalWidth) + "\n")
    stringBuilder.append(description)
    stringBuilder.toString()
  }
}
