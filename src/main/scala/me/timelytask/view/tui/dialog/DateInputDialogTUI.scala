package me.timelytask.view.tui.dialog

import com.github.nscala_time.time.Imports.DateTime
import me.timelytask.view.tui.TuiUtils.createLine
import me.timelytask.view.viewmodel.dialogmodel.{DialogModel, InputDialogModel}
import org.jline.reader.impl.history.DefaultHistory
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.Terminal

import scala.util.{Try, Success, Failure}



class DateInputDialogTUI(override val dialogModel: Option[InputDialogModel[DateTime]],
                         override val currentView: Option[String],
                         override val terminal: Terminal) 
  extends TUIDialog[DateTime] {

  override def apply(): Option[DateTime] = {
    if dialogModel.isEmpty | currentView.isEmpty then return None
    
    var break = false
    
    val terminalWidth = terminal.getWidth
    val dialogString = createDialogString(dialogModel.get.description, terminalWidth)
    val viewWithDialog = overlapString(currentView.get, dialogString)
    terminal.writer().println(viewWithDialog)

    // TODO: Implement the date input dialog correctly
    val history = new DefaultHistory()
    val reader: LineReader = LineReaderBuilder.builder()
      .terminal(terminal)
      .history(history) // Attach the history
      .variable(LineReader.HISTORY_SIZE, 0) // Disable history size
      .build()
  
    
    history.purge() // Clear the history before each input
    val input = reader.readLine("> ", null, 
      dialogModel.get.default.map(_.toString("yyyy-MM-dd")).getOrElse(""))
    history.purge() // Clear the history after input
    
    Try[Option[DateTime]]{
      Some(DateTime.parse(input))
    } match {
      case Success(value) => value
      case Failure(exception) => None
    }
  }

  private def createDialogString(description: String, terminalWidth: Int): String = {
    val stringBuilder = new StringBuilder()
    stringBuilder.append(createLine(terminalWidth) + "\n")
    stringBuilder.append(description + " (Format: yyyy-MM-dd)")
    stringBuilder.toString()
  }

}
