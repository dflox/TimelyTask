package view
import view.UtilTUI._

import com.github.nscala_time.time.Imports.*
import model.Task

class CalendarTUI {

  // Variables
  val format = "m"
  val timeColumn = "| Time  |"
  val timeColumnLength = timeColumn.length
  val headerPeriodFormat = "Calendar+DD. - DD. MMM. YY"
  val headerLetters = headerPeriodFormat.length // the amount of space(letters) the period String takes

  // variables used to set the specific time format
  val hours = 8 // The amount of hours the Rows show
  val startAt = 6.75 // The time the Rows start at





  def createTable(startDay: DateTime, period: Int, terminalHeight: Int, terminalWidth: Int): String = {
    val heightAvailable: Int = terminalHeight - 11
    val width: Int = terminalWidth

    // Variables
    val daysList = getDaySpan(startDay, period)
    var table = List[String]()
    table = table :+ timeColumn
    daysList.foreach(day => table = table :+ day.dayOfWeek().getAsText + "|") // dayList toString
    val letterAmount = table.map(_.length).sum
    val spaceBetween = (width - table.head.length - period) / period
    val actualWidth = timeColumnLength + period + (period * spaceBetween)
    val builder = new StringBuilder()

    // Creating
    builder.append(welcomeMessage())
    builder.append(header(actualWidth, startDay))
    // Create the TopRow
    builder.append(timeColumn)
    daysList.foreach(day => builder.append(columnSpacer(day.dayOfWeek().getAsText, spaceBetween, format) + "|")) // print the days
    builder.append("\n")
    builder.append(createLine(actualWidth) + "\n")
    // Create the time rows
    val (interval, maxLines) = calculateInterval(heightAvailable, hours)
    builder.append(createRows(startAt, hours, interval, period, spaceBetween))
    builder.append(createLine(actualWidth) + "\n")
    builder.append(alignTop(terminalHeight, maxLines) + "\n")

    builder.toString()

  }
    // Align the text (by adding spaces) to the left, right or middle
    def columnSpacer(text: String, totalSpace: Int, format: String): String = {
      cutText(text, totalSpace)
      var space = totalSpace - text.length
      if space < 0 then space = 0

      format match {
        case "l" => text + createSpace(space) // left
        case "m" => if (space % 2 == 0) createSpace(space / 2) + text + createSpace(space / 2) else createSpace(space / 2) + text + createSpace(space / 2 + 1) // middle
        case "r" => createSpace(math.max(space, 0)) + text // right
        }
    }
    def header(actualWidth: Int, dateToday: DateTime): String = {
      val builder = new StringBuilder()
      // Create the header
      builder.append(createLine(actualWidth) + "\n")
      builder.append("Calender" + createSpace(actualWidth - headerLetters) + getDatePeriod(getFirstDayOfWeek(dateToday), 7, "dd.", "dd. MMM yy", " - ") + "\n")
      builder.append(createLine(actualWidth) + "\n")
      builder.toString()
    }

    // Create the rows with the time
    def createRows(startTime: Double, hours: Double, interval: Double, period: Int, spaceBetween: Int): String = {
      val lines = (hours / interval).toInt
      val builder = new StringBuilder()
      for (i <- 0 until lines) {
        var hour = startTime + i * interval
        if (hour >= 24) hour = hour % 24

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

    // Calculate the interval between the hours and the amount of lines
    def calculateInterval(lines: Int, hours: Double): (Double, Int) = {

      val possibleIntervals = List(4.0, 2.0, 1.0, 0.5, 0.25)
      val rawInterval = hours / lines

      val chosenInterval = possibleIntervals.filter(_ >= rawInterval).min
      val maxLines = (hours / chosenInterval).toInt

      (chosenInterval, math.min(lines, maxLines))
    }

}
