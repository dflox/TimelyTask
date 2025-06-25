package me.timelytask.model.tag

import java.util.UUID

case class Tag(name: String, description: String, uuid: UUID = UUID.randomUUID())