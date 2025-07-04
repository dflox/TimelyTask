package me.timelytask.model.priority

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import scalafx.scene.paint.Color
import java.util.UUID

// Import the extension methods to make them available in the test.
import me.timelytask.model.priority.*

class PriorityExtensionsSpec extends AnyWordSpec with Matchers {

  // A fixture to hold the initial Priority object for our tests.
  // This keeps the tests clean and avoids repetition.
  trait Fixture {
    val initialColor: Color = null
    val updatedColor: Color = null // Another null is fine for testing the change.

    val initialPriority: Priority = Priority(
      name = "High",
      description = "Urgent tasks",
      rank = 1,
      color = initialColor,
      daysPreDeadline = 3,
      postponable = false,
      uuid = UUID.randomUUID()
    )
  }

  "The Priority extension methods" should {

    "withName should return a new Priority with an updated name" in new Fixture {
      val newName = "Critical"
      val updatedPriority = initialPriority.withName(newName)

      updatedPriority.name shouldBe newName
      updatedPriority.rank shouldBe initialPriority.rank // Check that other fields are unchanged.
      initialPriority.name shouldBe "High" // Verify immutability.
    }

    "withDescription should return a new Priority with an updated description" in new Fixture {
      val newDescription = "For critical path tasks only"
      val updatedPriority = initialPriority.withDescription(newDescription)

      updatedPriority.description shouldBe newDescription
      initialPriority.description shouldBe "Urgent tasks"
    }

    "withRank should return a new Priority with an updated rank" in new Fixture {
      val newRank = 0
      val updatedPriority = initialPriority.withRank(newRank)

      updatedPriority.rank shouldBe newRank
      initialPriority.rank shouldBe 1
    }

    "withColor should return a new Priority with an updated color" in new Fixture {
      val updatedPriority = initialPriority.withColor(updatedColor)

      updatedPriority.color shouldBe updatedColor
      initialPriority.color shouldBe initialColor
    }

    "withDaysPreDeadline should return a new Priority with an updated pre-deadline day count" in new Fixture {
      val newDays = 5
      val updatedPriority = initialPriority.withDaysPreDeadline(newDays)

      updatedPriority.daysPreDeadline shouldBe newDays
      initialPriority.daysPreDeadline shouldBe 3
    }

    "withPostponable should return a new Priority with an updated postponable status" in new Fixture {
      val newStatus = true
      val updatedPriority = initialPriority.withPostponable(newStatus)

      updatedPriority.postponable shouldBe newStatus
      initialPriority.postponable shouldBe false
    }

    "withUUID should return a new Priority with an updated UUID" in new Fixture {
      val newUuid = UUID.randomUUID()
      val updatedPriority = initialPriority.withUUID(newUuid)

      updatedPriority.uuid shouldBe newUuid
      initialPriority.uuid shouldNot be(newUuid)
    }

    "ensure all methods return a new instance (immutability check)" in new Fixture {
      initialPriority.withName("new") should not be theSameInstanceAs(initialPriority)
      initialPriority.withRank(99) should not be theSameInstanceAs(initialPriority)
      initialPriority.withPostponable(true) should not be theSameInstanceAs(initialPriority)
    }
  }
}