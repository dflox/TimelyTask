package view

import com.github.nscala_time.time.Imports.*
import controller.*
import org.jline.terminal.{Terminal, TerminalBuilder}
import util.TimeSelection
import view.UtilTUI.*
import view.model.CalendarModel
import view.tui.CalendarTUI

class Window {

  // Variables
  val dateTime: DateTime = DateTime.now()
  // set the width of the calendar to the terminal width
  val terminal: Terminal = TerminalBuilder.builder().system(true).build()
  val terminalWidth: Int = terminal.getWidth
  val terminalHeight: Int = terminal.getHeight

  // Instance of the CalendarTUI
  val calendarTUI: View[CalendarModel] = new CalendarTUI()
  val taskController: TaskController = new TaskController(calendarTUI)


  // Functions

  // RUN
  def run(): Unit = {
    val builder = new StringBuilder()
    builder.append(welcomeMessage())
    print(clearTerminal())
    builder.append(calendarTUI.update(new CalendarModel(timeSelection = new TimeSelection(DateTime.now(), 7, 8.hours), terminalHeight = terminalHeight, terminalWidth = terminalWidth, tasks = taskController.tasks)))
    print(builder.toString())
  }
}
