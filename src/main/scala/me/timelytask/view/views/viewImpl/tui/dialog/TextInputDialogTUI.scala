package me.timelytask.view.views.viewImpl.tui.dialog

import me.timelytask.view.viewmodel.dialogmodel.{DialogModel, InputDialogModel}
import me.timelytask.view.views.viewImpl.tui.TuiUtils.createLine
import org.jline.reader.impl.history.DefaultHistory
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.Terminal

class TextInputDialogTUI(override val dialogModel: Option[InputDialogModel[String]],
                         override val currentView: Option[String],
                         override val terminal: Terminal) extends TUIDialog[String] {

  def apply(): Option[String] = {
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
