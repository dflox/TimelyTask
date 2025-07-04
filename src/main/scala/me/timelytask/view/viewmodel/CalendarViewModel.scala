package me.timelytask.view.viewmodel

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.settings.CALENDAR
import me.timelytask.model.utility.TimeSelection
import me.timelytask.model.Model
import me.timelytask.model.task.Task
import me.timelytask.view.viewmodel.dialogmodel.DialogModel
import me.timelytask.view.viewmodel.elemts.{FocusElementGrid, Focusable, TaskCollection}

case class CalendarViewModel(timeSelection: TimeSelection = TimeSelection.defaultTimeSelection,
                             override val model: Model,
                             protected var focusElementGrid: Option[FocusElementGrid] = None)
  extends ViewModel[CALENDAR, CalendarViewModel] {

  // Variables
  val format = "m"
  val timeColumn = "| Time  |"
  val timeColumnLength: Int = timeColumn.length

  // variables used to set the specific time format
  val startAt: LocalTime = new LocalTime(6, 45, 0) // The time the Rows start at
  private val minWidthTimeColoumn = 7 // The minimum width of the time column
  private val minWidthColoumn = 3
  val minTerminalWidth: Int = 2 + minWidthTimeColoumn +
    timeSelection.dayCount * (minWidthColoumn + 1)
  val headerHeight = 5
  val footerHeight = 1
  val timeFormat = "HH:mm"
  val dayFormat = "EEE dd.MM"

  protected var taskToEdit: Option[Task] = None

  def getTaskToEdit: Option[Task] = taskToEdit

  override def interact(inputGetter: Option[DialogModel[?]] => Option[?])
  : Option[CalendarViewModel] = {

    def getFocusedElement: Option[Focusable[?]] = focusElementGrid match
      case Some(feg) => feg.getFocusedElement
      case None => None

    def getOptionInput(focusable: Option[Focusable[?]]): Option[Task] = focusable match
      case Some(taskCollection) => inputGetter(Some(taskCollection.dialogModel)) match
        case Some(task: Task) => Some(task)
        case _ => None
      case None => None

    taskToEdit = getOptionInput(getFocusedElement)

    if taskToEdit.isDefined then Some(this)
    else None
  }

  def buildFocusElementGrid(timeSlice: Period, rowCount: Int, timeSelection: TimeSelection =
  timeSelection, tasks: List[Task] = model.tasks, focusedElement: Option[Focusable[?]] = None)
  : FocusElementGrid = {
    var newFocusElementGrid = new FocusElementGrid(
      width = timeSelection.dayCount,
      height = rowCount)

    for (row <- 0 until rowCount) {
      val startTime = timeSelection.day.withPeriodAdded(timeSlice, row)
      (0 until timeSelection.dayCount -1)
        .foreach(day => {
          val timeSliceInterval = new Interval(startTime.withPeriodAdded(1.day, day), timeSlice)
          newFocusElementGrid = newFocusElementGrid.setElement(
              day,
              row,
              Some(TaskCollection(tasks.filter(
                    task => timeSliceInterval.contains(task.scheduleDate))
              ))).getOrElse(newFocusElementGrid)
          }
        )
    }

    if focusedElement.isDefined && focusedElement.get.isInstanceOf[TaskCollection] then
      newFocusElementGrid = newFocusElementGrid.setFocusToElement(focusedElement).getOrElse(
        newFocusElementGrid.setFocusToElement(
          newFocusElementGrid.elementsList.find(focusable => {
            focusable match {
              case Some(taskCollection: TaskCollection) => taskCollection.getTasks.contains(
                focusedElement
                  .asInstanceOf[TaskCollection].getTasks.head)
              case _ => false
            }
          }).flatten).getOrElse(newFocusElementGrid))
    focusElementGrid = Some(newFocusElementGrid)
    newFocusElementGrid
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
