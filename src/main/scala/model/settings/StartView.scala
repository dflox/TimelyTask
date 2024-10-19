package model.settings

enum StartView {
  case CALENDAR, TABLE, KANBAN
  
  override def toString: String = this match {
    case CALENDAR => "calendar"
    case TABLE => "table"
    case KANBAN => "kanban"
  }
}
object StartView {
    def fromString(s: String): StartView = s match {
      case "calendar" => CALENDAR
      case "table" => TABLE
      case "kanban" => KANBAN
    }
  }