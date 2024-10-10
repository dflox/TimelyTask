import controller._
import view._
import model._

def main(args:Array[String]): Unit = {
//  if width > terminalWidth then {
//    println(s"The current calender width ($width) is too large for the terminal width ($terminalWidth). Please adjust the width to be smaller than the terminal width.")
//    System.exit(0)
//  }
  val tui: TUI = new TUI()
  tui.run()
}
