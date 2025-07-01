package me.timelytask.model.tag

import java.util.UUID

extension (tag: Tag) {
  /**
   * Creates a new Tag with the specified name.
   * @param name the name of the tag
   * @return a new Tag instance with the specified name
   */
  def withName(name: String): Tag = tag.copy(name = name)

  /**
   * Creates a new Tag with the specified description.
   * @param description the description of the tag
   * @return a new Tag instance with the specified description
   */
  def withDescription(description: String): Tag = tag.copy(description = description)

  /**
   * Creates a new Tag with the specified UUID.
   * @param uuid the UUID of the tag
   * @return a new Tag instance with the specified UUID
   * @note use with caution, as UUIDs are typically generated automatically.
   */
  def withUUID(uuid: UUID): Tag = tag.copy(uuid = uuid)
}