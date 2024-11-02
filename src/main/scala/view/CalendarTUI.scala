package view

import com.github.nscala_time.time.Imports.*
import controller.*
import model.Task
import model.TimeSelection
import view.UtilTUI.*
import view.View
import view.*

object CalendarTUI extends TUIView {
  override def update(viewModel: ViewModel): String = {
    update(viewModel, TUIModel.default)
  }
  
  override def update(viewModel: ViewModel, TUIModel: TUIModel): String = {
    val calendarViewModel: CalendarViewModel = viewModel.asInstanceOf[CalendarViewModel]
    import calendarViewModel.*
    import TUIModel.*
    
    // Variables
    val heightAvailable: Int = terminalHeight - 11
    val width: Int = terminalWidth
    val daysList = timeSelection.getDaySpan

    // Calculate Actual Width
    var table = List[String]()
    table = table :+ timeColumn
    daysList.foreach(day => table = table :+ day.dayOfWeek().getAsText + "|") // dayList toString

    // Calculate the possible Space that each day has (subtract the timeColumn and the seperator for the days)
    val spacePerColumn = (width - table.head.length - timeSelection.dayCount) / timeSelection.dayCount
    val actualWidth = timeColumnLength + timeSelection.dayCount + (timeSelection.dayCount * spacePerColumn)

    // Start Building Output
    val builder = new StringBuilder()

    // Creating
    builder.append(header(actualWidth, timeSelection, headerLetterCount))
    // Create the TopRow
    builder.append(timeColumn)
    daysList.foreach(day => builder.append(columnSpacer(day.dayOfWeek().getAsText, spacePerColumn, format) + "|")) // print the days
    builder.append("\n")
    builder.append(createLine(actualWidth) + "\n")

    // Create the time and task rows
    val (intervals, lines) = calculateInterval(heightAvailable, timeSelection.interval)
    builder.append(createRows(intervals, timeSelection, model.tasks, spacePerColumn))
    builder.append(createLine(actualWidth) + "\n")
    builder.append(alignTop(terminalHeight, lines) + "\n")

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

  // Create the rows with time and tasks
  def createRows(intervals: Seq[Interval], timeSelection: TimeSelection, tasks: List[Task], spacePerColumn: Int): String = {
    val builder = new StringBuilder()

    val format = "HH:mm"

    for (interval <- intervals) {
      builder.append(interval.getStart.toString(format))
      for (day <- timeSelection.getDaySpan) {
        val tasksTimeslot = tasks.filter(task => interval.contains(task.deadline.date))
        val taskString = tasksTimeslot.map(_.name).mkString(", ")
        builder.append(columnSpacer(taskString, spacePerColumn, "l") + "|")
      }
      builder.append("\n")
    }
    builder.toString()
  }

  // Calculate the intervals between the hours and the amount of lines
  private def calculateInterval(lines: Int, timeFrame: Interval): (Seq[Interval], Int) = {
    val possibleIntervals = List(4.0, 2.0, 1.0, 0.5, 0.25)

    val optimalInterval: Double = timeFrame.toDurationMillis / lines / 1000.0 / 3600.0

    val chosenInterval: Double = possibleIntervals.filter(_ >= optimalInterval).min
    val maxLines = (timeFrame.toDurationMillis / 1000.0 / 3600.0 / chosenInterval).toInt

    val chosenLines = math.min(lines, maxLines)

    // Create the intervals
    val intervals = for (i <- 0 until chosenLines) yield {
      val start = timeFrame.getStart + convDoubleToTime(chosenInterval * i)
      val end = start + convDoubleToTime(chosenInterval)
      new Interval(start, end)
    }
    (intervals, chosenLines)
  }

  private def convDoubleToTime(d: Double): Period = {
    val dd = d % 24
    val hours = dd.toInt
    val minutes = ((dd - hours) * 60).toInt
    new Period(0, 0, 0, 0, minutes, hours, 0, 0)
  }

}
