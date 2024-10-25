package model

import java.util.UUID
import model.settings.DataType

case class Tag(name: String, description: String) {
  val uuid: UUID = UUID.randomUUID()
}