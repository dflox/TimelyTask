package view

import com.github.nscala_time.time.Imports.*
import jline.{Terminal, TerminalFactory}
import model.Task
import view.UtilTUI.*

class TUI extends View[List[Task]] {

  // Variables

  val format = "m"
  val dateToday: DateTime = DateTime.now()
  val timeColumn = "| Time  |"
  val headerLetters = 25 // The amount of letters in "Calendar+DD. - DD. MMM. YY"
  val hours = 8 // The amount of hours the Rows show
  val startAt = 6.75 // The time the Rows start at

  // set the width of the calendar to the terminal width
  val terminal: Terminal = TerminalFactory.get()
  val terminalWidth: Int = terminal.getWidth
  val terminalHeight: Int = terminal.getHeight
  val lines: Int = terminalHeight - 11
  val width: Int = terminalWidth
  // Functions

  // RUN
  def render(model: List[Task]): String = {
    val builder = new StringBuilder()
    builder.append("\u001b[2J") // clear the terminal
    builder.append(createTable(width, getFirstDayOfWeek(dateToday), 7))
    builder.toString()
  }

  // Create the table
  def createTable(width: Int, startDay: DateTime, period: Int): String = {
    // Variables
    val daysList = getDaySpan(startDay, period)
    var table = List[String]()
    table = table :+ timeColumn
    daysList.foreach(day => table = table :+ day.dayOfWeek().getAsText + "|")
    val letterAmount = table.map(_.length).sum
    val spaceBetween = (width - table.head.length - period) / period
    val actualWidth = timeColumn.length + period + (period * spaceBetween)
    val builder = new StringBuilder()

    // Creating
    val userName = System.getProperty("user.name")
    val greeting = "Hello " + userName + ","
    // Welcome message
    builder.append(greeting + "\n")
    builder.append("Welcome to TimelyTask! \n")
    // Create the header
    builder.append(createLine(actualWidth) + "\n")
    builder.append("Calender" + createSpace(actualWidth - headerLetters) + getDatePeriod(getFirstDayOfWeek(dateToday), 7, "dd.", "dd. MMM yy", " - ") + "\n")
    builder.append(createLine(actualWidth) + "\n")
    // Create the TopRow
    builder.append(timeColumn)
    daysList.foreach(day => builder.append(columnSpacer(day.dayOfWeek().getAsText, spaceBetween, format) + "|")) // print the days
    builder.append("\n")
    builder.append(createLine(actualWidth) + "\n")
    // Create the time rows
    val (interval, maxLines) = calculateInterval(lines, hours)
    builder.append(createRows(startAt, hours, interval, period, spaceBetween))
    builder.append(createLine(actualWidth) + "\n")
    builder.append(alignTop(terminalHeight, maxLines) + "\n")

    builder.toString()

  }

  // Align the text by adding spaces to the left, right or middle
  def columnSpacer(text: String, totalSpace: Int, format: String): String = {
    // check if the text is longer than the total space, if so cut it
    if (text.length > totalSpace) {
      return text.substring(0, totalSpace)
    }
    val space = totalSpace - text.length
    format match {
      case "l" => text + createSpace(space) // left
      case "m" => if (space % 2 == 0) createSpace(space / 2) + text + createSpace(space / 2) else createSpace(space / 2) + text + createSpace(space / 2 + 1) // middle
      case "r" => createSpace(space) + text // right
    }
  }

  // Calculate the interval between the hours and the amount of lines
  def calculateInterval(lines: Int, hours: Double): (Double, Int) = {
    if (lines <= 0) {
      throw new IllegalArgumentException("Number of lines must be greater than 0")
    }

    val possibleIntervals = List(4.0, 2.0, 1.0, 0.5, 0.25)
    val rawInterval = hours / lines

    val chosenInterval = possibleIntervals.filter(_ >= rawInterval).min
    val maxLines = (hours / chosenInterval).toInt

    (chosenInterval, math.min(lines, maxLines))
  }

  // Create the rows with the time
  def createRows(startTime: Double, hours: Double, interval: Double, period: Int, spaceBetween: Int): String = {
    val lines = (hours / interval).toInt
    val builder = new StringBuilder()
    for (i <- 0 until lines) {
      var hour = startTime + i * interval
      if (hour >= 24) hour -= 24
      val hourWhole = hour.toInt
      val minute = ((hour - hourWhole) * 60).toInt
      val hourString = f"$hourWhole%02d:$minute%02d" // format the hour to be 2 digits
      builder.append(s"| $hourString |")
      for (_ <- 0 until period) {
        builder.append(createSpace(spaceBetween) + "|")
      }
      builder.append("\n")
    }
    builder.toString()
  }
  
}
