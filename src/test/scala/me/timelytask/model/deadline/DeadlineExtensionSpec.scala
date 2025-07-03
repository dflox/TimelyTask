package me.timelytask.model.deadline

import com.github.nscala_time.time.Imports.DateTime
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.EitherValues // Import for easy testing of Either

// Import the extension methods to make them available in the test.
import me.timelytask.model.deadline.*

class DeadlineExtensionSpec extends AnyWordSpec with Matchers with EitherValues {

  // A fixture to hold common date values and Deadline instances for tests.
  trait Fixture {
    val baseDate: DateTime = new DateTime("2024-08-15T12:00:00Z")
    val earlierDate: DateTime = baseDate.minusDays(5)
    val laterDate: DateTime = baseDate.plusDays(5)

    // A deadline with no initialDate set.
    val deadlineWithoutInitial: Deadline = Deadline(date = baseDate, initialDate = None, completionDate = None)

    // A deadline with a valid initialDate.
    val deadlineWithInitial: Deadline = Deadline(date = baseDate, initialDate = Some(earlierDate), completionDate = None)
  }

  "The Deadline extension methods" when {

    // --- Testing the `private[model] def withDate` method ---
    "withDate is called" should {

      "update only the date if the new date is after the existing initialDate" in new Fixture {
        // Act
        val updatedDeadline = deadlineWithInitial.withDate(laterDate)

        // Assert
        updatedDeadline.date shouldBe laterDate
        // The initialDate should NOT have changed.
        updatedDeadline.initialDate should contain(earlierDate)
      }

      "update both date and initialDate if the new date is before the existing initialDate" in new Fixture {
        // Arrange: Create a deadline where the initial date is the base date.
        val d = Deadline(date = baseDate, initialDate = Some(baseDate), completionDate = None)

        // Act: Set the date to be earlier.
        val updatedDeadline = d.withDate(earlierDate)

        // Assert
        updatedDeadline.date shouldBe earlierDate
        // The initialDate should also be moved back.
        updatedDeadline.initialDate should contain(earlierDate)
      }
    }

    // --- Testing `def withCompletionDate` method ---
    "withCompletionDate is called" should {

      "return a Right with an updated Deadline if the completion date is after the deadline" in new Fixture {
        // Act
        val result = deadlineWithoutInitial.withCompletionDate(laterDate)

        // Assert
        result.isRight shouldBe true
        // .value from EitherValues safely unwraps the Right
        result.value.completionDate should contain(laterDate)
        result.value.date shouldBe baseDate // The original date should be unchanged
      }

      "return a Right if the completion date is the same as the deadline date" in new Fixture {
        // Act
        val result = deadlineWithoutInitial.withCompletionDate(baseDate)

        // Assert
        result.isRight shouldBe true
        result.value.completionDate should contain(baseDate)
      }

      "return a Left with an error message if the completion date is before the deadline" in new Fixture {
        // Act
        val result = deadlineWithoutInitial.withCompletionDate(earlierDate)

        // Assert
        result.isLeft shouldBe true
        // .left.value from EitherValues safely unwraps the Left
        result.left.value should include("before the deadline date")
      }
    }

    // --- Testing `def withInitialDate` method ---
    "withInitialDate is called" should {

      "return a Right with an updated Deadline if the initial date is before the deadline" in new Fixture {
        // Act
        val result = deadlineWithoutInitial.withInitialDate(earlierDate)

        // Assert
        result.isRight shouldBe true
        result.value.initialDate should contain(earlierDate)
        result.value.date shouldBe baseDate
      }

      "return a Right if the initial date is the same as the deadline date" in new Fixture {
        // Act
        val result = deadlineWithoutInitial.withInitialDate(baseDate)

        // Assert
        result.isRight shouldBe true
        result.value.initialDate should contain(baseDate)
      }

      "return a Left with an error message if the initial date is after the deadline" in new Fixture {
        // Act
        val result = deadlineWithoutInitial.withInitialDate(laterDate)

        // Assert
        result.isLeft shouldBe true
        result.left.value should include("after the deadline date")
      }
    }
  }
}