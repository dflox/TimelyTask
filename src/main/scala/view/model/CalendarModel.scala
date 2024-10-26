package view.model

import com.github.nscala_time.time.Imports.DateTime
import view.tui.*
import util.TimeSelection
import model.Task

sealed trait ViewModel {
  val userName: String = System.getProperty("user.name")
  val today: DateTime = DateTime.now()
}

case class CalendarModel(timeSelection: TimeSelection,
                         terminalHeight: Int,
                         terminalWidth: Int,
                         tasks: List[Task])
  extends ViewModel {
  // Variables
  val format = "m"
  val timeColumn = "| Time  |"
  val timeColumnLength: Int = timeColumn.length
  val headerPeriodFormat = "Calendar+DD. - DD. MMM. YY"
  val headerLetterCount: Int = headerPeriodFormat.length // the amount of space(letters) the period String takes

  // variables used to set the specific time format
  val startAt = 6.75 // The time the Rows start at
  val minWidthTimeColoumn = 7 // The minimum width of the time column
  val minWidthColoumn = 3
  val minTerminalWidth: Int = 2 + minWidthTimeColoumn + timeSelection.dayCount * (minWidthColoumn + 1)
}
