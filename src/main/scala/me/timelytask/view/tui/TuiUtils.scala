package me.timelytask.view.tui

import com.github.nscala_time.time.Imports.*

// TODO: Change into package object 

object TuiUtils {
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

  // Get a list of days starting with a given day and going forward for a given TimePeriod
  def getDaySpan(startDay: DateTime, period: Int): List[DateTime] = {
    (0 until period).map(startDay + _.days).toList
  }

  // Align the text to the top by adding newlines
  def alignTop(totalLines: Int, used: Int): String = {
    val unused: Int = totalLines - used - 3
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

  // Align the text (by adding spaces) to the left, right or middle
  def columnSpacer(text: String, totalSpace: Int, format: String): String = {
    cutText(text, totalSpace)
    var space = totalSpace - text.length
    if space < 0 then space = 0

    format match {
      case "l" => text + createSpace(space) // left
      case "m" => if (space % 2 == 0) createSpace(space / 2) + text + createSpace(space / 2) else
                                                                                               createSpace(
                                                                                                 space /
                                                                                                   2) +
                                                                                                 text +
                                                                                                 createSpace(
                                                                                                   space /
                                                                                                     2 +
                                                                                                     1) // middle
      case "r" => createSpace(math.max(space, 0)) + text // right
    }
  }

}
