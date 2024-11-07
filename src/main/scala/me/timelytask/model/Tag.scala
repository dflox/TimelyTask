package me.timelytask.model

import java.util.UUID

case class Tag(name: String, description: String) {
  val uuid: UUID = UUID.randomUUID()
}