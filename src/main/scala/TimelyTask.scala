//import controller.*
//import view.*
//import model.*
//import com.googlecode.lanterna.terminal._
//import com.googlecode.lanterna.terminal.virtual._
//import com.googlecode.lanterna.screen._
//import com.googlecode.lanterna.input.KeyType
//
//def main(args:Array[String]): Unit = {
////  if width > terminalWidth then {
////    println(s"The current calender width ($width) is too large for the terminal width ($terminalWidth). Please adjust the width to be smaller than the terminal width.")
////    System.exit(0)
////  }
//// Create a simple terminal using DefaultTerminalFactory
//// Use a headless terminal (virtual terminal)
//    val terminal = new DefaultTerminalFactory().setForceTextTerminal(true).createTerminal()
//
//    // Create a screen to manage terminal output
//    val screen = new TerminalScreen(terminal)
//    screen.startScreen()
//
//    // Print some text to the screen
//    screen.clear()
//    screen.newTextGraphics().putString(10, 10, "Hello, Lanterna in Headless Mode!")
//    screen.refresh()
//
//    // Wait for user input before closing
//    screen.readInput()  // This pauses until a key is pressed
//
//    // Clean up
//    screen.stopScreen()
//    terminal.close()
//  /*
//  val tui: TUI = new TUI()
//  tui.run()
//  */
//}
