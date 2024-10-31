package model

import model.settings.DataType

import java.util.UUID

case class Tag(name: String, description: Option[String]) {
  val uuid: UUID = UUID.randomUUID()
}