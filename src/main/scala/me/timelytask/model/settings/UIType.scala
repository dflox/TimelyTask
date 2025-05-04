package me.timelytask.model.settings

enum UIType {
  case GUI, TUI

  override def toString: String = this match {
    case GUI => "gui"
    case TUI => "tui"
  }

}

object UIType {
  def fromString(s: String): UIType = s match {
    case "gui" => GUI
    case "tui" => TUI
  }
}