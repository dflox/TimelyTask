package me.timelytask.view.viewmodel

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.{Model, Task}
import me.timelytask.model.settings.CALENDAR
import me.timelytask.model.utility.TimeSelection
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.elemts.{FocusElementGrid, TaskCollection}

case class CalendarViewModel(timeSelection: TimeSelection = TimeSelection.defaultTimeSelection, 
                             modelPublisher: Publisher[Model]) 
  extends ViewModel[CALENDAR](modelPublisher) {

  import CalendarViewModel.*

  protected var focusElementGrid: FocusElementGrid
  
  def getFocusElementGrid: FocusElementGrid = focusElementGrid
  
  // Variables
  val format = "m"
  val timeColumn = "| Time  |"
  val timeColumnLength: Int = timeColumn.length

  // variables used to set the specific time format
  val startAt: LocalTime = new LocalTime(6, 45, 0) // The time the Rows start at
  val minWidthTimeColoumn = 7 // The minimum width of the time column
  val minWidthColoumn = 3
  val minTerminalWidth: Int = 2 + minWidthTimeColoumn +
    timeSelection.dayCount * (minWidthColoumn + 1)
  val headerHeight = 5
  val footerHeight = 1
  val timeFormat = "HH:mm"
  val dayFormat = "EEE dd.MM"
  
  def buildFocusElementGrid(timeSlice: Period, rowCount: Int, timeSelection: TimeSelection, 
                             tasks: List[Task]): FocusElementGrid = {
    var newFocusElementGrid = new FocusElementGrid(width = timeSelection.dayCount, height = rowCount)
    for (line <- 0 until rowCount) {
      val startTime = timeSelection.day.withPeriodAdded(timeSlice, line)
      (0 until timeSelection.dayCount)
        .foreach(day => {
          val timeSliceInterval = new Interval(startTime.withPeriodAdded(1.day, day),timeSlice)
          tasks.filter(task => timeSliceInterval.contains(task.scheduleDate)) match {
            case tasksTimeslot if tasksTimeslot.nonEmpty =>
              newFocusElementGrid = newFocusElementGrid.setElement(day, line,
                TaskCollection(tasksTimeslot, timeSliceInterval))
          }
        })
    }
    focusElementGrid = newFocusElementGrid
    focusElementGrid
  }

  // Calculate the intervals between the hours and the amount of lines
  def calculatePeriod(linesAvailable: Int, minLinesPerEntry: Int = 1, timeFrame: Interval): (Period,
    Int) = {
    val possibleIntervalsMinutes = List(24.0, 12.0, 10.0, 8.0, 6.0, 4.0, 2.0, 1.0, 0.5, 0.25)
      .map(_ * 60)

    val optimalIntervalMinutes: Double = timeFrame.toDurationMillis /
      (linesAvailable / minLinesPerEntry) / 1000.0 / 60.0

    val chosenIntervalMinutes: Double = possibleIntervalsMinutes.filter(_ >= optimalIntervalMinutes)
      .min
    val countOfChosenIntervals = (timeFrame.toDurationMillis / chosenIntervalMinutes / 1000.0 /
      60.0).toInt

    val rowCount = math.min(linesAvailable, countOfChosenIntervals)
    val timeSlice: Period = chosenIntervalMinutes.toInt.minutes.normalizedStandard()
    (timeSlice, rowCount)
  }
}
