package me.timelytask.model.settings

import scalafx.scene.paint.Color

object ThemeSystem {
  // Semantic color names with their default values
  case class Theme(
                    // Base backgrounds from darker to lighter
                    background0: String = "#17171c", // Darkest - main background
                    background1: String = "#333399", // Secondary background (e.g., panels)
                    background2: String = "#1a1aff", // Tertiary background (e.g., hover states)

                    // Surface colors for elevated elements
                    surface0: String = "#1DC3AA", // Default surface
                    surface1: String = "#25DEC1", // Elevated surface
                    surface2: String = "#2ADFC4", // Highest elevation

                    // Text colors from darker to lighter
                    text0: String = "#2191FB", // Muted text
                    text1: String = "#4BA6FB", // Primary text
                    text2: String = "#73BAFC", // Emphasized text

                    // Accent colors
                    accent0: String = "#BA274A", // Primary accent
                    accent1: String = "#841C26", // Secondary accent
                    accent2: String = "#7dcfff", // Tertiary accent

                    // Semantic colors for states and feedback
                    success: String = "#9ece6a", // Success/confirmation
                    warning: String = "#e0af68", // Warnings/caution
                    error: String = "#f7768e", // Errors/destructive
                    info: String = "#2ac3de", // Information/help

                    // Interactive element colors
                    interactive0: String = "#7aa2f7", // Default state
                    interactive1: String = "#89ddff", // Hover state
                    interactive2: String = "#bb9af7", // Active state

                    // Border colors
                    border0: String = "#1a1b26", // Subtle borders
                    border1: String = "#24283b", // Default borders
                    border2: String = "#414868" // Emphasized borders
                  )

  // Color utilities for different platforms
  object ColorSupport {
    // Terminal color support - extends previous Terminal object
    object Terminal {

      import scala.collection.mutable

      // Cache for hex to 256 color conversions
      private val colorCache = mutable.Map[String, String]()

      def hexToTerminal256(hex: String): String = {
        colorCache.getOrElseUpdate(hex, {
          val color = Color.web(hex)
          val (r, g, b) = (
            (color.red * 255).toInt,
            (color.green * 255).toInt,
            (color.blue * 255).toInt
          )

          // Convert to 256-color space
          val terminalColor = if (r == g && g == b) {
            val gray = Math.round(r / 255.0f * 23)
            if (gray == 0) 16 else if (gray == 23) 231 else 232 + gray
          } else {
            val tr = Math.round(r / 255.0f * 5)
            val tg = Math.round(g / 255.0f * 5)
            val tb = Math.round(b / 255.0f * 5)
            16 + (36 * tr) + (6 * tg) + tb
          }

          s"\u001B[38;5;${terminalColor}m"
        })
      }

      def bgHexToTerminal256(hex: String): String = {
        val color = hexToTerminal256(hex)
        color.replace("[38", "[48")
      }

      val RESET = "\u001B[0m"
      val BOLD = "\u001B[1m"
      val DIM = "\u001B[2m"
      val ITALIC = "\u001B[3m"
      val UNDERLINE = "\u001B[4m"
      val BLINK = "\u001B[5m"
      val REVERSE = "\u001B[7m"
      val HIDDEN = "\u001B[8m"

      def colored(text: String, color: String): String = s"$color$text$RESET"

      def colored(text: String, color: String, bgcolor: String): String = {
        s"$color$bgcolor$text$RESET"
      }
    }

    // ScalaFX color support
    object ScalaFX {
      def hexToColor(hex: String): Color = Color.web(hex)
    }
  }
}