package me.timelytask.model.priority

import scalafx.scene.paint.Color

import java.util.UUID

/**
 * Represents a priority level for tasks.
 * @param name the name of the priority
 * @param description a description of the priority
 * @param rank the rank of the priority, lower numbers indicate higher priority
 * @param color the color associated with the priority, used for UI representation
 * @param daysPreDeadline the number of days before the deadline when this priority should be considered
 * @param postponable whether the priority can be postponed
 * @param uuid a unique identifier for the priority, defaults to a new UUID if not provided
 */
case class Priority(name: String,
                    description: String,
                    rank: Int,
                    color: Color,
                    daysPreDeadline: Int,
                    postponable: Boolean,
                    uuid: UUID = UUID.randomUUID()) {
  def < (other: Priority): Boolean = {
    this.rank < other.rank
  }
  def == (other: Priority): Boolean = {
    this.uuid == other.uuid
  }
  def != (other: Priority): Boolean = {
    this.uuid != other.uuid
  }
  def > (other: Priority): Boolean = {
    this.rank > other.rank
  }

  def <= (other: Priority): Boolean = {
    this.rank <= other.rank
  }
  def >= (other: Priority): Boolean = {
    this.rank >= other.rank
  }

  def isValid: Boolean = {
    name.nonEmpty && description.nonEmpty && rank >= 0 && daysPreDeadline >= 0
  }
}