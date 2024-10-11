package view

import com.github.nscala_time.time.Imports.*
import jline.{Terminal, TerminalFactory}


class TUI {

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
  def run(): Unit = {
    println("\u001b[2J") // clear the terminal
    printTable(width, getFirstDayOfWeek(dateToday), 7)
  }

  // Print the table
  def printTable(width: Int, startDay: DateTime, period: Int): Unit = {
    val daysList = getDaySpan(startDay, period)
    var table = List[String]()
    table = table :+ timeColumn
    daysList.foreach(day => table = table :+ day.dayOfWeek().getAsText + "|")
    val letterAmount = table.map(_.length).sum
    val spaceBetween = (width - table.head.length - period) / period
    val actualWidth = timeColumn.length + period + (period * spaceBetween)


    // Printing
    val userName = System.getProperty("user.name")
    val greeting = "Hello " + userName + ","
    // Welcome message
    println(greeting)
    println("Welcome to TimelyTask! \n")
    // print the header
    printLine(actualWidth)
    println("Calender" + createSpace(actualWidth - headerLetters) + getDatePeriod(getFirstDayOfWeek(dateToday), 7, "dd.", "dd. MMM yy", " - "))
    printLine(actualWidth)
    // print the TopRow
    print(timeColumn)
    daysList.foreach(day => print(columnSpacer(day.dayOfWeek().getAsText, spaceBetween, format) + "|")) // print the days
    println()
    printLine(actualWidth)
    // print the time rows
    val (interval, maxLines) = calculateInterval(lines, hours)
    printRows(startAt, hours, interval, period, spaceBetween)
    printLine(actualWidth)
    alignTop(terminalHeight, maxLines)

  }

  // Align the text to the top of the terminal
  def alignTop(totalLines: Int, used: Int): Int = {
    val unused: Int = totalLines - used - 11
    if (unused > 0) {
      for (_ <- 0 until unused) {
        println()
      }
    }
    unused
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

  // Print the rows with the time
  def printRows(startTime: Double, hours: Double, interval: Double, period: Int, spaceBetween: Int): Unit = {
    val lines = (hours / interval).toInt
    for (i <- 0 until lines) {
      var hour = startTime + i * interval
      if (hour >= 24) hour -= 24
      val hourWhole = hour.toInt
      val minute = ((hour - hourWhole) * 60).toInt
      val hourString = f"$hourWhole%02d:$minute%02d" // format the hour to be 2 digits
      print(s"| $hourString |")
      for (_ <- 0 until period) {
        print(createSpace(spaceBetween) + "|")
      }
      println()
    }
  }


  // Get the first Day of the week
  def getFirstDayOfWeek(day: DateTime): DateTime = {
    day - (day.getDayOfWeek - 1).days
  }

  // Get a time period in a given Format as String starting with a given day and going forward for a given TimePeriod
  def getDatePeriod(day: DateTime, timePeriod: Int, formatStart: String, formatEnd: String, separator: String): String = {
    val lastDay: DateTime = day + (timePeriod - 1).days
    day.toString(formatStart) + separator + lastDay.toString(formatEnd)
  }

  // write a given amount of spaces
  def createSpace(length: Int): String = {
    " " * length
  }

  // write a given amount of lines
  def createLine(length: Int): String = {
    "-" * length
  }

  // print a line with a given width
  def printLine(lineWidth: Int): Unit = {
    println(createLine(lineWidth))
  }

  // Get a list of days starting with a given day and going forward for a given TimePeriod
  def getDaySpan(startDay: DateTime, period: Int): List[DateTime] = {
    (0 until period).map(startDay + _.days).toList
  }
}
