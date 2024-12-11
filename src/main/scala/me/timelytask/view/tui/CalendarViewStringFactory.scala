package me.timelytask.view.tui

import com.github.nscala_time.time.Imports.*
import me.timelytask.controller.ThemeManager.{getTerminalBgColor, getTerminalColor}
import me.timelytask.model.Task
import me.timelytask.model.settings.CALENDAR
import me.timelytask.model.settings.ThemeSystem.ColorSupport.Terminal.{BOLD, ITALIC, colored}
import me.timelytask.model.utility.TimeSelection
import me.timelytask.view.tui.TuiUtils.*
import me.timelytask.view.viewmodel.elemts.TaskCollection
import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}

object CalendarViewStringFactory extends StringFactory {
  override def buildString(viewModel: ViewModel[CALENDAR]): String = {
    buildString(viewModel, ModelTUI.default)
  }

  override def buildString(viewModel: ViewModel[CALENDAR], tuiModel: ModelTUI): String = {
    val calendarViewModel: CalendarViewModel = viewModel.asInstanceOf[CalendarViewModel]
    import calendarViewModel.*
    import tuiModel.*

    // Variables
    val heightAvailable: Int = terminalHeight - headerHeight - footerHeight
    val width: Int = terminalWidth
    val daySpan = timeSelection.getDaySpan
    val colorText1 = getTerminalColor(_.text1)
    val colorText2 = getTerminalColor(_.text2)
    val colorFocused = getTerminalBgColor(_.background2)
    val colorUnfocused = getTerminalBgColor(_.background0)

    // Calculate Actual Width
    var table = List[String]()
    table = table :+ timeColumn
    daySpan.foreach(day => table = table :+ day.toString(dayFormat) + "|") // dayList toString

    // Calculate the possible Space that each day has (subtract the timeColumn and the seperator
    // for the days)
    val spacePerColumn = (width - table.head.length - timeSelection.dayCount) /
      timeSelection.dayCount
    val actualWidth = timeColumnLength + timeSelection.dayCount +
      (timeSelection.dayCount * spacePerColumn)

    // Generate Grid
    val (timeSlice, rowCount) = calculatePeriod(heightAvailable, 1, timeSelection.timeFrameInterval)
    getFocusElementGrid match {
      case None => buildFocusElementGrid(timeSlice, rowCount, timeSelection, model().tasks)
      case Some(focusElementGrid) => if focusElementGrid.height != rowCount then
        buildFocusElementGrid(timeSlice, rowCount, timeSelection, model().tasks)
    }

    // Start Building Output
    val builder = new StringBuilder()

    // Creating
    builder.append(header(actualWidth, timeSelection))
    // Create the TopRow
    builder.append(colored(timeColumn, colorText1))
    daySpan.foreach(day => builder.append(
      colored(columnSpacer(day.toString(dayFormat), spacePerColumn, format),
        if day.toLocalDate.equals(today.toLocalDate) then colorText2 + BOLD
        else colorText1) + "|")) //

    builder.append("\n")

    builder.append(createLine(actualWidth) + "\n")

    // Create the time and task rows
    builder.append(createRows(timeSlice, rowCount, timeSelection, spacePerColumn,
      colorText1, calendarViewModel))

    builder.append(createLine(actualWidth) + "\n")
    builder.append(alignTop(terminalHeight, rowCount + headerHeight + footerHeight) + "\n")

    builder.toString()
  }

  // Align the text (by adding spaces) to the left, right or middle
  def columnSpacer(text: String, totalSpace: Int, format: String): String = {
    cutText(text, totalSpace)
    var space = totalSpace - text.length
    if space < 0 then space = 0

    format match {
      case "l" => text + createSpace(space) // left
      case "m" => if (space % 2 == 0) createSpace(space / 2) + text + createSpace(space / 2)
                  else createSpace(space / 2) + text + createSpace(space / 2 + 1) // middle
      case "r" => createSpace(math.max(space, 0)) + text // right
    }
  }

  def header(actualWidth: Int, timeSelection: TimeSelection): String = {
    val title = "Calendar"
    val dateformat = "dd. - dd. MMM. yyyy"
    val headerLetterCount: Int = (title + dateformat)
      .length // the amount of space(letters) the period String takes
    val builder = new StringBuilder()
    // Create the header
    builder.append(createLine(actualWidth) + "\n")
    builder.append(colored(title, getTerminalColor(_.text2) + BOLD) +
      createSpace(actualWidth - headerLetterCount) + colored(timeSelection
      .toString("dd.", "dd. MMM yyyy", " - "), getTerminalColor(_.text2) + ITALIC) + "\n")
    builder.append(createLine(actualWidth) + "\n")
    builder.toString()
  }

  // Create the rows with time and tasks
  def createRows(timeSlice: Period, rowCount: Int, timeSelection: TimeSelection,
                 spacePerColumn: Int, timeTextColor: String,
                 calendarViewModel: CalendarViewModel): String
  = {
    val builder = new StringBuilder()

    val format = "| HH:mm |"

    calendarViewModel.getFocusElementGrid match {
      case Some(focusElementGrid) => focusElementGrid.getElements.transpose.zipWithIndex.foreach( 
        (row, rowNum) => {
          builder.append(colored(timeSelection.day.withPeriodAdded(timeSlice, rowNum).toString(
            format),timeTextColor))
          row.foreach {
            case taskCollection: TaskCollection =>
              builder.append(columnSpacer(taskCollection.toString, spacePerColumn, "l") + "|")
            case _ => builder.append(columnSpacer("", spacePerColumn, "l") + "|")
          }
          builder.append("\n")
        })
      case None => throw new Exception("FocusElementGrid not initialized")
    }
    builder.toString()
  }
}

