package me.timelytask

object ColorUtils {
  // ANSI escape codes for terminal colors
  object Terminal {
    // Standard foreground colors (3-bit: 8 colors)
    val BLACK = "\u001B[30m"
    val RED = "\u001B[31m"
    val GREEN = "\u001B[32m"
    val YELLOW = "\u001B[33m"
    val BLUE = "\u001B[34m"
    val PURPLE = "\u001B[35m"
    val CYAN = "\u001B[36m"
    val WHITE = "\u001B[37m"

    // Bright foreground colors
    val BRIGHT_BLACK = "\u001B[90m"
    val BRIGHT_RED = "\u001B[91m"
    val BRIGHT_GREEN = "\u001B[92m"
    val BRIGHT_YELLOW = "\u001B[93m"
    val BRIGHT_BLUE = "\u001B[94m"
    val BRIGHT_PURPLE = "\u001B[95m"
    val BRIGHT_CYAN = "\u001B[96m"
    val BRIGHT_WHITE = "\u001B[97m"

    // Background colors
    val BG_BLACK = "\u001B[40m"
    val BG_RED = "\u001B[41m"
    val BG_GREEN = "\u001B[42m"
    val BG_YELLOW = "\u001B[43m"
    val BG_BLUE = "\u001B[44m"
    val BG_PURPLE = "\u001B[45m"
    val BG_CYAN = "\u001B[46m"
    val BG_WHITE = "\u001B[47m"

    // Bright background colors
    val BG_BRIGHT_BLACK = "\u001B[100m"
    val BG_BRIGHT_RED = "\u001B[101m"
    val BG_BRIGHT_GREEN = "\u001B[102m"
    val BG_BRIGHT_YELLOW = "\u001B[103m"
    val BG_BRIGHT_BLUE = "\u001B[104m"
    val BG_BRIGHT_PURPLE = "\u001B[105m"
    val BG_BRIGHT_CYAN = "\u001B[106m"
    val BG_BRIGHT_WHITE = "\u001B[107m"

    // 256 color mode (8-bit colors)
    def color256(n: Int): String = s"\u001B[38;5;${n}m"
    def bgColor256(n: Int): String = s"\u001B[48;5;${n}m"

    // True color mode (24-bit colors)
    def trueColor(r: Int, g: Int, b: Int): String = s"\u001B[38;2;$r;$g;${b}m"
    def bgTrueColor(r: Int, g: Int, b: Int): String = s"\u001B[48;2;$r;$g;${b}m"

    // Styles
    val RESET = "\u001B[0m"
    val BOLD = "\u001B[1m"
    val DIM = "\u001B[2m"
    val ITALIC = "\u001B[3m"
    val UNDERLINE = "\u001B[4m"
    val BLINK = "\u001B[5m"
    val REVERSE = "\u001B[7m"
    val HIDDEN = "\u001B[8m"

    // Helper method to wrap text with color
    def colored(text: String, color: String): String = s"$color$text$RESET"

    // Convert hex color to nearest 256-color terminal color
    def hexToTerminal256(hex: String): String = {
      val color = GUI.fromHex(hex)
      val (r, g, b) = (color.getRed, color.getGreen, color.getBlue)

      // Convert to 256-color space
      val terminalColor = if (r == g && g == b) {
        // Grayscale colors (232-255)
        val gray = Math.round(r / 255.0f * 23)
        if (gray == 0) 16 else if (gray == 23) 231 else 232 + gray
      } else {
        // RGB colors (16-231)
        val tr = Math.round(r / 255.0f * 5)
        val tg = Math.round(g / 255.0f * 5)
        val tb = Math.round(b / 255.0f * 5)
        16 + (36 * tr) + (6 * tg) + tb
      }

      color256(terminalColor)
    }

    // Test if terminal supports true color
    def supportsTrueColor: Boolean = {
      Option(System.getenv("COLORTERM")).exists(term =>
        term.toLowerCase == "truecolor" || term.toLowerCase == "24bit"
      )
    }
  }

  // GUI colors as hex values and java.awt.Color objects
  object GUI {
    import java.awt.Color

    val BLACK = new Color(0x000000)
    val RED = new Color(0xFF0000)
    val GREEN = new Color(0x00FF00)
    val YELLOW = new Color(0xFFFF00)
    val BLUE = new Color(0x0000FF)
    val PURPLE = new Color(0xFF00FF)
    val CYAN = new Color(0x00FFFF)
    val WHITE = new Color(0x0000F5)

    def fromHex(hex: String): Color = {
      Color.decode(if (hex.startsWith("#")) hex else s"#$hex")
    }

    // Convert Color to hex string
    def toHex(color: Color): String = {
      f"#${color.getRGB & 0xFFFFFF}%06X"
    }
  }
}