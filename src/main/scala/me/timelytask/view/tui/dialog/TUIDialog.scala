package me.timelytask.view.tui.dialog

import me.timelytask.view.views.Dialog
import org.jline.terminal.Terminal

trait TUIDialog[T] extends Dialog[T, String] {

  val terminal: Terminal
  val terminalWidth: Int = terminal.getWidth

  protected def overlapString(background: String, foreground: String): String = {
    val backgroundLines = background.split("\n")
    val foregroundLines = foreground.split("\n")
    val lineDiff = backgroundLines.length - foregroundLines.length
    if (lineDiff < 0) {
      foreground
    } else {
      val overlappedLines = backgroundLines
      for (i <- lineDiff until backgroundLines.length) {
        overlappedLines(i) = foregroundLines(i - lineDiff)
      }
      overlappedLines.mkString("\n")
    }
  }
}
