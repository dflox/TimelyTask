package me.timelytask.model

import scalafx.scene.paint.Color

import java.util.UUID

case class Priority(name: String,
                    description: String,
                    rank: Int,
                    color: Color,
                    daysPreDeadline: Int,
                    postponable: Boolean,
                    uuid: UUID = UUID.randomUUID())