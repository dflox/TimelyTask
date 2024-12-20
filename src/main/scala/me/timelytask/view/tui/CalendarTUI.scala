package me.timelytask.view.tui

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Task
import me.timelytask.model.utility.TimeSelection
import me.timelytask.view.tui.UtilTUI.*
import me.timelytask.view.viewmodel.{CalendarViewModel, TUIModel, ViewModel}


object CalendarTUI extends TUIView {
  override def update(viewModel: ViewModel): String = {
    update(viewModel, TUIModel.default)
  }
  
  override def update(viewModel: ViewModel, TUIModel: TUIModel): String = {
    val calendarViewModel: CalendarViewModel = viewModel.asInstanceOf[CalendarViewModel]
    import TUIModel.*
    import calendarViewModel.*
    
    // Variables
    val heightAvailable: Int = terminalHeight - headerHeight - footerHeight
    val width: Int = terminalWidth
    val daysList = timeSelection.getDaySpan

    // Calculate Actual Width
    var table = List[String]()
    table = table :+ timeColumn
    daysList.foreach(day => table = table :+ day.toString(dayFormat) + "|") // dayList toString

    // Calculate the possible Space that each day has (subtract the timeColumn and the seperator for the days)
    val spacePerColumn = (width - table.head.length - timeSelection.dayCount) / timeSelection.dayCount
    val actualWidth = timeColumnLength + timeSelection.dayCount + (timeSelection.dayCount * spacePerColumn)

    // Start Building Output
    val builder = new StringBuilder()

    // Creating
    builder.append(header(actualWidth, timeSelection))
    // Create the TopRow
    builder.append(timeColumn)
    daysList.foreach(day => builder.append(columnSpacer(day.toString(dayFormat), spacePerColumn, format) + "|")) // print the days
    builder.append("\n")
    builder.append(createLine(actualWidth) + "\n")

    // Create the time and task rows
    val (timeSlice, lines) = calculatePeriod(heightAvailable, timeSelection.timeFrameInterval)
    builder.append(createRows(timeSlice, lines, timeSelection, model.tasks, spacePerColumn))
    builder.append(createLine(actualWidth) + "\n")
    builder.append(alignTop(terminalHeight, lines + headerHeight + footerHeight) + "\n")

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
  def header(actualWidth: Int, timeSelection: TimeSelection): String = {
    val title = "Calendar"
    val dateformat = "dd. - dd. MMM. yyyy"
    val headerLetterCount: Int = (title+dateformat).length // the amount of space(letters) the period String takes
    val builder = new StringBuilder()
    // Create the header
    builder.append(createLine(actualWidth) + "\n")
    builder.append(title + createSpace(actualWidth - headerLetterCount) + timeSelection.toString("dd.", "dd. MMM yyyy", " - ") + "\n")
    builder.append(createLine(actualWidth) + "\n")
    builder.toString()
  }

  // Create the rows with time and tasks
  def createRows(timeSlice: Period, lines: Int, timeSelection: TimeSelection, tasks: List[Task], spacePerColumn: Int): String = {
    val builder = new StringBuilder()

    val format = "| HH:mm |"

    for (line <- 0 until lines) {
      val startTime = timeSelection.day.withPeriodAdded(timeSlice, line)
      val timeSlicesPerDay: Seq[Interval] = (0 until timeSelection.dayCount)
        .map(day => new Interval(startTime.withPeriodAdded(1.day, day), timeSlice))

      builder.append(startTime.toString(format))

      timeSlicesPerDay.foreach(interval => {
        val tasksTimeslot = tasks.filter(task => interval.contains(task.deadline.date))
        val taskString = tasksTimeslot.map(_.name).mkString(", ")
        builder.append(columnSpacer(taskString, spacePerColumn, "l") + "|")
      })

      builder.append("\n")
    }
    builder.toString()
  }

  // Calculate the intervals between the hours and the amount of lines
  def calculatePeriod(linesAvailable: Int, timeFrame: Interval): (Period, Int) = {
    val possibleIntervalsMinutes = List(24.0, 12.0, 10.0, 8.0, 6.0, 4.0, 2.0, 1.0, 0.5, 0.25).map(_ * 60)

    val optimalIntervalMinutes: Double = timeFrame.toDurationMillis / linesAvailable / 1000.0 / 60.0

    val chosenIntervalMinutes: Double = possibleIntervalsMinutes.filter(_ >= optimalIntervalMinutes).min
    val countOfChosenIntervals = (timeFrame.toDurationMillis / chosenIntervalMinutes / 1000.0 / 60.0).toInt

    val chosenLines = math.min(linesAvailable, countOfChosenIntervals)
    val timeSlice: Period = chosenIntervalMinutes.toInt.minutes.normalizedStandard()
    (timeSlice, chosenLines)
  }
}