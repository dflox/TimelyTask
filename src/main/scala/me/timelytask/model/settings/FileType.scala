package me.timelytask.model.settings

enum FileType {
  case JSON, XML, YAML

  override def toString: String = this match {
    case JSON => "json"
    case XML => "xml"
    case YAML => "yaml"
  }
}

object FileType {
  def fromString(s: String): FileType = s match {
    case "json" => JSON
    case "xml" => XML
    case "yaml" => YAML
  }
}