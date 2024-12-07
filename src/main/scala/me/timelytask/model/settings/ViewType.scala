package me.timelytask.model.settings

enum ViewType {
  case CALENDAR, TABLE, KANBAN, SETTINGS, TASKEdit

  override def toString: String = this match {
    case CALENDAR => "calendar"
    case TABLE => "table"
    case KANBAN => "kanban"
    case SETTINGS => "settings"
    case TASKEdit => "task"
  }
}

object ViewType {
  def fromString(s: String): ViewType = s match {
    case "calendar" => CALENDAR
    case "table" => TABLE
    case "kanban" => KANBAN
    case "settings" => SETTINGS
    case "task" => TASKEdit
  }
}