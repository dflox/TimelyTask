package me.timelytask.model.settings

enum Theme {
  case LIGHT, DARK
  
  override def toString: String = this match {
    case LIGHT => "light"
    case DARK => "dark"
  }
}

object Theme {
  def fromString(s: String): Theme = s match {
    case "light" => LIGHT
    case "dark" => DARK
  }
}