package model.settings

import model._

enum DataType {
  case CONFIG, TASK, PRIORITY, TAG, STATE

  override def toString: String = this match {
    case CONFIG => "config"
    case TASK => "tasks"
    case PRIORITY => "priorities"
    case TAG => "tags"
    case STATE => "states"
  }
}
object DataType {
  def fromString(fileName: String): DataType = fileName match {
    case "config" => DataType.CONFIG
    case "tasks" => DataType.TASK
    case "priorities" => DataType.PRIORITY
    case "tags" => DataType.TAG
    case "states" => DataType.STATE
  }
}