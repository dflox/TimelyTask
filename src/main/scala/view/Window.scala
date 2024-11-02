package view

import com.github.nscala_time.time.Imports.*
import controller.*
import org.jline.terminal.{Terminal, TerminalBuilder}
import util.TimeSelection
import view.UtilTUI.*
import view.CalendarModel
import view.tui.*

class Window {

  // Variables
  val dateTime: DateTime = DateTime.now()
  // set the width of the calendar to the terminal width
  val terminal: Terminal = TerminalBuilder.builder().system(true).build()
  val terminalWidth: Int = terminal.getWidth
  val terminalHeight: Int = terminal.getHeight

  // Instance of the CalendarTUI
  val taskController: TaskController = new TaskController(CalendarTUI)


  // Functions

  // RUN
  def run(): Unit = {
    val builder = new StringBuilder()
    builder.append(welcomeMessage())
    print(clearTerminal())
    builder.append(CalendarTUI.update(new CalendarModel(timeSelection = new TimeSelection(DateTime.now(), 7, 8.hours), terminalHeight = terminalHeight, terminalWidth = terminalWidth, tasks = taskController.tasks)))
    print(builder.toString())
  }
}
