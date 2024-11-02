package model.settings

import view.TUIView
import view.CalendarTUI

enum ViewType {
  case CALENDAR, TABLE, KANBAN, SETTINGS, TASK
  
  override def toString: String = this match {
    case CALENDAR => "calendar"
    case TABLE => "table"
    case KANBAN => "kanban"
    case SETTINGS => "settings"
    case TASK => "task"
  }
  
  // TODO: Implement the rest of the cases
  def getTUIView: TUIView = this match {
    case CALENDAR => CalendarTUI
    case TABLE => ???
    case KANBAN => ???
    case SETTINGS => ???
    case TASK => ???
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