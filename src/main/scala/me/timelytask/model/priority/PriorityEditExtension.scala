package me.timelytask.model.priority

import scalafx.scene.paint.Color

import java.util.UUID

extension (priority: Priority) {
  /**
   * Creates a copy of the priority with the specified name.
   * @param name the new name for the priority
   * @return a new Priority instance with the updated name
   */
  def withName(name: String): Priority = {
    priority.copy(name = name)
  }

  /**
   * Creates a copy of the priority with the specified description.
   * @param description the new description for the priority
   * @return a new Priority instance with the updated description
   */
  def withDescription(description: String): Priority = {
    priority.copy(description = description)
  }

  /**
   * Creates a copy of the priority with the specified rank.
   * @param rank the new rank for the priority
   * @return a new Priority instance with the updated rank
   */
  def withRank(rank: Int): Priority = {
    priority.copy(rank = rank)
  }

  /**
   * Creates a copy of the priority with the specified color.
   * @param color the new color for the priority
   * @return a new Priority instance with the updated color
   */
  def withColor(color: Color): Priority = {
    priority.copy(color = color)
  }

  /**
   * Creates a copy of the priority with the specified number of days before the deadline.
   * @param daysPreDeadline the new number of days before the deadline
   * @return a new Priority instance with the updated daysPreDeadline
   */
  def withDaysPreDeadline(daysPreDeadline: Int): Priority = {
    priority.copy(daysPreDeadline = daysPreDeadline)
  }

  /**
   * Creates a copy of the priority with the specified postponable status.
   * @param postponable whether the priority can be postponed
   * @return a new Priority instance with the updated postponable status
   */
  def withPostponable(postponable: Boolean): Priority = {
    priority.copy(postponable = postponable)
  }

  /**
   * Creates a copy of the priority with the specified UUID.
   * @param uuid the new UUID for the priority
   * @return a new Priority instance with the updated UUID
   */
  def withUUID(uuid: UUID): Priority = {
    priority.copy(uuid = uuid)
  }
}