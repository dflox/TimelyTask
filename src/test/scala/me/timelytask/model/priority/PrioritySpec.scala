// src/test/scala/me/timelytask/model/priority/PrioritySpec.scala

package me.timelytask.model.priority

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import scalafx.scene.paint.Color

import java.util.UUID

class PrioritySpec extends AnyWordSpec with Matchers {

  // A Fixture to set up common objects for our tests, reducing duplication.
  trait Fixture {
    val uuid1: UUID = UUID.fromString("11111111-1111-1111-1111-111111111111")
    val uuid2: UUID = UUID.fromString("22222222-2222-2222-2222-222222222222")

    // A valid priority to use as a base
    val basePriority: Priority = Priority("High", "High priority task", 1, Color.Red, 5, true, uuid1)

    // Priorities for rank comparison
    val highRank: Priority = basePriority.copy(rank = 1, uuid = UUID.randomUUID())
    val mediumRank: Priority = basePriority.copy(rank = 5, uuid = UUID.randomUUID())
    val lowRank: Priority = basePriority.copy(rank = 10, uuid = UUID.randomUUID())
  }

  "A Priority object" should {

    "be created with a default UUID if none is provided" in {
      val p = Priority("Test", "Desc", 1, Color.Blue, 1, false)
      p.uuid shouldBe a[UUID]
    }

    "be created with a specified UUID" in {
      val specifiedUuid = UUID.randomUUID()
      val p = Priority("Test", "Desc", 1, Color.Blue, 1, false, specifiedUuid)
      p.uuid should be(specifiedUuid)
    }

    // --- Testing Equality ---
    "be equal to another Priority with the same UUID, regardless of other fields" in new Fixture {
      val samePriorityWithDifferentData = Priority("Low", "Different desc", 99, Color.Green, 0, false, uuid1)

      (basePriority == samePriorityWithDifferentData) should be(true)
      basePriority.equals(samePriorityWithDifferentData) should be(true)
    }

    "not be equal to another Priority with a different UUID" in new Fixture {
      val differentPriority = basePriority.copy(uuid = uuid2)
      (basePriority == differentPriority) should be(false)
    }

    "not be equal to an object of a different type" in new Fixture {
      basePriority.equals("a string") should be(false)
    }

    "correctly implement inequality" in new Fixture {
      val differentPriority = basePriority.copy(uuid = uuid2)
      (basePriority != differentPriority) should be(true)
    }

    // --- Testing Rank Comparison ---
    "correctly compare ranks with '<' and '>'" in new Fixture {
      (highRank < mediumRank) should be(true)
      (mediumRank < lowRank) should be(true)
      (lowRank > highRank) should be(true)
      (mediumRank > lowRank) should be(false)
    }

    "correctly compare ranks with '<=' and '>='" in new Fixture {
      (highRank <= mediumRank) should be(true)
      (mediumRank >= highRank) should be(true)
      (lowRank >= lowRank) should be(true)
      (highRank <= highRank) should be(true)
      (lowRank <= highRank) should be(false)
    }

    // --- Testing Validation ---
    "isValid should return true for a valid priority" in new Fixture {
      basePriority.isValid should be(true)
    }

    "isValid should return false if name is empty" in new Fixture {
      val invalidPriority = basePriority.copy(name = "")
      invalidPriority.isValid should be(false)
    }

    "isValid should return false if description is empty" in new Fixture {
      val invalidPriority = basePriority.copy(description = "")
      invalidPriority.isValid should be(false)
    }

    "isValid should return false if rank is negative" in new Fixture {
      val invalidPriority = basePriority.copy(rank = -1)
      invalidPriority.isValid should be(false)
    }

    "isValid should return false if daysPreDeadline is negative" in new Fixture {
      val invalidPriority = basePriority.copy(daysPreDeadline = -5)
      invalidPriority.isValid should be(false)
    }
  }
}