package view

import com.github.nscala_time.time.Imports.*
import util.TimeSelection

object UtilTUI {
  // write a given amount of dashes
  def createLine(length: Int): String = {
    require(length >= 0, "Length cannot be negative")
    "-" * length
  }

  // write a given amount of spaces
  def createSpace(length: Int): String = {
    require(length >= 0, "Length cannot be negative")
    " " * length
  }

  // Align the text to the top by adding newlines
  def alignTop(totalLines: Int, used: Int): String = {
    val unused: Int = totalLines - used
    if (unused > 0) {
      "\n" * unused
    } else {
      ""
    }
  }

  // check if the text is longer than the total space, if so cut it
  def cutText(text: String, totalSpace: Int): String = {
    if (text.length > totalSpace) {
      text.substring(0, totalSpace)
    } else {
      text
    }
  }
  def welcomeMessage(): String = {
    val userName = System.getProperty("user.name")
    val greeting = s"Hello $userName,\nWelcome to TimelyTask!\n"
    greeting
  }

  def clearTerminal(): String = {
    "\u001b[H\u001b[2J"
  }

}
