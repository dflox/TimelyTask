package me.timelytask.model.settings

import me.timelytask.view.tui.{CalendarTUI, TUIView}

enum ViewType {
  case CALENDAR, TABLE, KANBAN, SETTINGS, TASK

  override def toString: String = this match {
    case CALENDAR => "calendar"
    case TABLE => "table"
    case KANBAN => "kanban"
    case SETTINGS => "settings"
    case TASK => "task"
  }
}

object ViewType {
  def fromString(s: String): ViewType = s match {
    case "calendar" => CALENDAR
    case "table" => TABLE
    case "kanban" => KANBAN
    case "settings" => SETTINGS
    case "task" => TASK
  }
}