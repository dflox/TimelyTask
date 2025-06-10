package me.timelytask.model

import java.util.UUID

case class Tag(name: String, description: String, uuid: UUID = UUID.randomUUID())