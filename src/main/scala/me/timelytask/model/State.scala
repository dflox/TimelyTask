package me.timelytask.model

import java.awt.Color
import java.util.UUID

class State(var name: String, var description: String, var color: Color) {
  val uuid: UUID = UUID.randomUUID()
}