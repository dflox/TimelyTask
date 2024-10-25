package model

import java.util.UUID
import model.settings.DataType

case class Tag(name: String, description: Option[String]) {
  val uuid: UUID = UUID.randomUUID()
}