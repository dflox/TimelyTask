package view

import com.github.nscala_time.time.Imports.LocalTime
import model.{Model, Task, TimeSelection}
import view.viewmodel.ViewModel

case class CalendarViewModel(model: Model, timeSelection: TimeSelection) extends ViewModel {
  import CalendarViewModel._
  // Variables
  val format = "m"
  val timeColumn = "| Time  |"
  val timeColumnLength: Int = timeColumn.length

  // variables used to set the specific time format
  val startAt: LocalTime = new LocalTime(6, 45, 0) // The time the Rows start at
  val minWidthTimeColoumn = 7 // The minimum width of the time column
  val minWidthColoumn = 3
  val minTerminalWidth: Int = 2 + minWidthTimeColoumn + timeSelection.dayCount * (minWidthColoumn + 1)
  val headerHeight = 5
  val footerHeight = 1
  val timeFormat = "HH:mm"
  val dayFormat = "EEE dd"
}

object CalendarViewModel {
  def createViewModel(model: Model, timeSelection: TimeSelection): ViewModel = {
    CalendarViewModel(model, timeSelection)
  }
  def createViewModel(model: Model): ViewModel = {
    CalendarViewModel(model, TimeSelection.defaultTimeSelection)
  }
  
  def copy(viewModel: ViewModel, timeSelection: TimeSelection): ViewModel = {
    val calendarViewModel: CalendarViewModel = viewModel.asInstanceOf[CalendarViewModel]
    CalendarViewModel(calendarViewModel.model, calendarViewModel.timeSelection)
  }
}