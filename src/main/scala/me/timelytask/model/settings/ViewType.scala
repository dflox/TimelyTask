package me.timelytask.model.settings

sealed trait ViewType {
  override def toString: String
}

object ViewType {
  def fromString(s: String): ViewType = s match {
    case "calendar" => CALENDAR
    case "table" => TABLE
    case "kanban" => KANBAN
    case "settings" => SETTINGS
    case "taskedit" => TASKEdit
  }
  
  def getAll: Vector[ViewType] = Vector(CALENDAR, TABLE, KANBAN, SETTINGS)
}

trait CALENDAR extends ViewType

case object CALENDAR extends ViewType {
  override def toString: String = "calendar"
}

trait TABLE extends ViewType

case object TABLE extends ViewType {
  override def toString: String = "table"
}

trait KANBAN extends ViewType

case object KANBAN extends ViewType {
  override def toString: String = "kanban"
}

trait SETTINGS extends ViewType

case object SETTINGS extends ViewType {
  override def toString: String = "settings"
}

trait TASKEdit extends ViewType

case object TASKEdit extends ViewType {
  override def toString: String = "taskedit"
}

trait StartUp extends ViewType