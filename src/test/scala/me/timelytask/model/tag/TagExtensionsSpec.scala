// src/test/scala/me/timelytask/model/tag/TagExtensionsSpec.scala

package me.timelytask.model.tag

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class TagExtensionsSpec extends AnyWordSpec with Matchers {

  // A Fixture to provide a clean, predictable starting Tag for each test.
  // Using a fixed UUID makes comparisons deterministic.
  trait Fixture {
    val initialTag: Tag = Tag(
      name = "Initial Name",
      description = "An initial description for the tag.",
      uuid = UUID.fromString("11111111-1111-1111-1111-111111111111")
    )
  }

  "A Tag extension method" should {

    "withName should return a new Tag with an updated name" in new Fixture {
      // Setup
      val newName = "Updated Tag Name"

      // Action
      val updatedTag = initialTag.withName(newName)

      // Assert
      updatedTag.name should be(newName)
      // Verify other fields were not changed
      updatedTag.description should be(initialTag.description)
      updatedTag.uuid should be(initialTag.uuid)
      // Verify the original object is immutable
      initialTag.name should be("Initial Name")
    }

    "withDescription should return a new Tag with an updated description" in new Fixture {
      // Setup
      val newDescription = "A completely new description."

      // Action
      val updatedTag = initialTag.withDescription(newDescription)

      // Assert
      updatedTag.description should be(newDescription)
      // Verify other fields were not changed
      updatedTag.name should be(initialTag.name)
      updatedTag.uuid should be(initialTag.uuid)
    }

    "withUUID should return a new Tag with an updated UUID" in new Fixture {
      // Setup
      val newUuid = UUID.randomUUID()

      // Action
      val updatedTag = initialTag.withUUID(newUuid)

      // Assert
      updatedTag.uuid should be(newUuid)
      // Verify other fields were not changed
      updatedTag.name should be(initialTag.name)
      updatedTag.description should be(initialTag.description)
    }
  }
}