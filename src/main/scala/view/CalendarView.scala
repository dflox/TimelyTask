package view

import com.github.nscala_time.time.Imports.*
import jline.Terminal
import model.*
import util.Align

case class CalendarView(align: Align, terminal: Terminal, startDate: Option[DateTime], startTime: Option[DateTime],
                        timeRange: Option[Period], tasks: List[Task]) {
  // Constants
  val dateToday: DateTime = DateTime.now()
  val timeColumn = "| Time  |"

  //val headerLetters = 
  
  val terminalWidth: Int = terminal.getWidth
  val terminalHeight: Int = terminal.getHeight
  val lines: Int = terminalHeight - 11
  val width: Int = terminalWidth
}
