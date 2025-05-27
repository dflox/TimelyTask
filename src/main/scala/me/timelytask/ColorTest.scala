package me.timelytask

import me.timelytask.ColorUtils.*
import me.timelytask.ColorUtils.Terminal.*

object ColorTest extends App {


  // Basic colors
  println(colored("Basic Red", RED))
  println(colored("Bright Red", BRIGHT_RED))

  // 256-color mode
  println(colored("Orange-ish", color256(214)))
  println(colored("Purple-ish", color256(93)))

  // True color (if supported)
  println(colored("Custom RGB", trueColor(255, 128, 0)))

  // Convert hex color to nearest terminal color
  val terminalOrange = hexToTerminal256("#FF8800")
  println(colored("Converted from hex", terminalOrange))

  // Combining styles
  println(s"$BOLD$ITALIC${color256(82)}Styled text$RESET")

  println(
    s"""
       |${color256(82)}This ${bgColor256(20)}is a test
       |a long,${bgColor256(24)} long test.
       |This is a ${color256(93)} highlight ${RESET} and this is not.
       """.stripMargin)

  //
  //  // Check if terminal supports true color
  //  if (supportsTrueColor) {
  //    println(colored("Using true color!", trueColor(100, 149, 237)))
  //  } else {
  //    // Fall back to 256 colors
  //    println(colored("Using 256 colors", color256(68)))
  //
  //    for (i <- 0 until 256) {
  //      println(colored(s"Color $i", color256(i)))
  //    }
  //
  //    for(i <- 0 until 256) {
  //      println(colored(s"Background Color $i", bgColor256(i)))
  //    }
  //  }

}
