package model.settings

import model.*

enum DataType {
  case CONFIG, TASK, PRIORITY, TAG, STATE

  def getFileName: String = this match {
    case CONFIG => "config"
    case TASK => "task"
    case PRIORITY => "priority"
    case TAG => "tag"
    case STATE => "state"
  }
}
object DataType {
  def fromString(fileName: String): DataType = fileName match {
    case "config" => DataType.CONFIG
    case "task" => DataType.TASK
    case "priority" => DataType.PRIORITY
    case "tag" => DataType.TAG
    case "state" => DataType.STATE
  }
}