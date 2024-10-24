package view

import com.github.nscala_time.time.Imports.*
import jline.{Terminal, TerminalFactory}
import model.Task
import view.UtilTUI._

class TUI extends View[List[Task]] {

  // Variables
  val dateTime: DateTime = DateTime.now()
  // set the width of the calendar to the terminal width
  val terminal: Terminal = TerminalFactory.get()
  val terminalWidth: Int = terminal.getWidth
  val terminalHeight: Int = terminal.getHeight

  // Instance of the CalendarTUI
  val calendarTUI = new CalendarTUI()

  // Functions

  // RUN
  def update(model: List[Task]): String = {
    val builder = new StringBuilder()
    print(clearTerminal())
    builder.append(calendarTUI.createTable(getFirstDayOfWeek(dateTime), 7, terminalHeight, terminalWidth))
    builder.toString()
  }
}
