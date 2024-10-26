package view.tui

import com.github.nscala_time.time.Imports.*
import controller.*
import model.Task
import util.TimeSelection
import view.UtilTUI.*
import view.View
import view.model.CalendarModel

class CalendarTUI() extends View[CalendarModel] {
  
  override def update(calModel: CalendarModel): String = {
    import calModel.*

    // Variables
    val heightAvailable: Int = terminalHeight - 11
    val width: Int = terminalWidth
    val daysList = timeSelection.getDaySpan
    
    // Calculate Actual Width
    var table = List[String]()
    table = table :+ timeColumn
    daysList.foreach(day => table = table :+ day.dayOfWeek().getAsText + "|") // dayList toString
    //val letterAmount = table.map(_.length).sum#
    // Calculate the possible Space that each day has (subtract the timeColumn and the seperator for the days)
    val spaceBetween = (width - table.head.length - timeSelection.dayCount) / timeSelection.dayCount
    val actualWidth = timeColumnLength + timeSelection.dayCount + (timeSelection.dayCount * spaceBetween)
    
    // Start Building Output
    val builder = new StringBuilder()

    // Creating
    builder.append(header(actualWidth, timeSelection, headerLetterCount))
    // Create the TopRow
    builder.append(timeColumn)
    daysList.foreach(day => builder.append(columnSpacer(day.dayOfWeek().getAsText, spaceBetween, format) + "|")) // print the days
    builder.append("\n")
    builder.append(createLine(actualWidth) + "\n")
    // Create the time rows
    val (interval, maxLines) = calculateInterval(heightAvailable, timeSelection.interval)
    builder.append(createRows(startAt, timeSelection.interval, interval, timeSelection.dayCount, spaceBetween))
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
      def header(actualWidth: Int, timeSelection: TimeSelection, headerLetterCount: Int): String = {
      val builder = new StringBuilder()
      // Create the header
      builder.append(createLine(actualWidth) + "\n")
      builder.append("Calender" + createSpace(actualWidth - headerLetterCount) + timeSelection.toString("dd.", "dd. MMM yy", " - ") + "\n")
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
    def calculateInterval(lines: Int, timeFrame: Interval): (interval: Double, maxLines: Int) = {

      val possibleIntervals = List(4.0, 2.0, 1.0, 0.5, 0.25)
      val rawInterval = timeFrame / lines

      val chosenInterval = possibleIntervals.filter(_ >= rawInterval).min
      val maxLines = (timeFrame / chosenInterval).toInt

      (chosenInterval, math.min(lines, maxLines))
    }

}
