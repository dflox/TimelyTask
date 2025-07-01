package me.timelytask.util.color

import me.timelytask.model.settings.ThemeSystem.{ColorSupport, Theme}
import scalafx.scene.paint.Color

// Theme management
object ThemeApplier {
  private var currentTheme = Theme()

  def setTheme(theme: Theme): Unit = {
    currentTheme = theme
  }

  def getTheme: Theme = currentTheme

  /**
   * Extracts a value from the current theme using a selector function.
   * This method contains the pure logic and is easy to test directly.
   */
  def getThemeValue(f: Theme => String): String = {
    f(currentTheme)
  }

  // Get color for terminal
  def getTerminalColor(f: Theme => String): String = {
    val hexValue = getThemeValue(f)
    ColorSupport.Terminal.hexToTerminal256(hexValue)
  }

  // Get background color for terminal
  def getTerminalBgColor(f: Theme => String): String = {
    val hexValue = getThemeValue(f)
    ColorSupport.Terminal.bgHexToTerminal256(hexValue)
  }

  // Get color for ScalaFX
  def getFXColor(f: Theme => String): Color = {
    val hexValue = getThemeValue(f)
    ColorSupport.ScalaFX.hexToColor(hexValue)
  }
}