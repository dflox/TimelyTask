package me.timelytask.view.tui.dialog

import me.timelytask.view.viewmodel.dialogmodel.DialogModel
import org.jline.terminal.Terminal

trait TUIDialog[T] {
  def getUserInput: Option[?]

  val dialogModel: Option[DialogModel[T]]
  val terminal: Terminal
  val currentView: Option[String]
  val terminalWidth: Int = terminal.getWidth


  def overlapString(background: String, foreground: String): String = {
    val backgroundLines = background.split("\n")
    val foregroundLines = foreground.split("\n")
    val lineDiff = backgroundLines.length - foregroundLines.length
    if (lineDiff < 0) {
      foreground
    } else {
      var overlappedLines = backgroundLines
      for (i <- lineDiff until backgroundLines.length) {
        overlappedLines(i) = foregroundLines(i - lineDiff)
      }
      overlappedLines.mkString("\n")
    }
  }
}
