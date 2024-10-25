package model

import model.settings.DataType

import java.awt.Color
import java.util.UUID

case class Priority(name: String, description: String, rank: Int, color: Color,
                    daysPreDeadline: Int, postponable: Boolean) {
  val uuid: UUID = UUID.randomUUID()
}