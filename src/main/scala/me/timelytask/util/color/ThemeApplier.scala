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

  // Get color for terminal
  def getTerminalColor(f: Theme => String): String = {
    ColorSupport.Terminal.hexToTerminal256(f(currentTheme))
  }

  // Get background color for terminal
  def getTerminalBgColor(f: Theme => String): String = {
    ColorSupport.Terminal.bgHexToTerminal256(f(currentTheme))
  }

  // Get color for ScalaFX
  def getFXColor(f: Theme => String): Color = {
    ColorSupport.ScalaFX.hexToColor(f(currentTheme))
  }
}