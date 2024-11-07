package me.timelytask.model.settings

enum AppType {
  case GUI, TUI

  override def toString: String = this match {
    case GUI => "gui"
    case TUI => "tui"
  }

}
object AppType {
  def fromString(s: String): AppType = s match {
    case "gui" => GUI
    case "tui" => TUI
  }
}