package me.timelytask.view.tui.dialog

import com.github.nscala_time.time.Imports.DateTime
import me.timelytask.view.tui.TuiUtils.createLine
import me.timelytask.view.viewmodel.dialogmodel.{DateInputDialogModel, DialogModel}
import org.jline.reader.impl.history.DefaultHistory
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.Terminal


class DateInputDialogTUI(val dialogModel: DialogModel, val terminal: Terminal) extends TUIDialog {

  def getUserInput: DateTime = {
    val dateInputDialogModel = dialogModel.asInstanceOf[DateInputDialogModel]
    val terminalWidth = terminal.getWidth
    val dialogString = createDialogString(dateInputDialogModel.description, terminalWidth)
    val viewWithDialog = overlapString(dateInputDialogModel.currentView, dialogString)
    terminal.writer().println(viewWithDialog)

    // TODO: Implement the date input dialog correctly
    val history = new DefaultHistory()
    val reader: LineReader = LineReaderBuilder.builder()
      .terminal(terminal)
      .history(history) // Attach the history
      .variable(LineReader.HISTORY_SIZE, 0) // Disable history size
      .build()

    history.purge() // Clear the history before each input
    val input = reader.readLine("> ")
    history.purge() // Clear the history after input
    DateTime.parse(input)
  }

  private def createDialogString(description: String, terminalWidth: Int): String = {
    val stringBuilder = new StringBuilder()
    stringBuilder.append(createLine(terminalWidth) + "\n")
    stringBuilder.append(description)
    stringBuilder.toString()
  }

}
