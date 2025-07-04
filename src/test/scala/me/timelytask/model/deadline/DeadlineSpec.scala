package me.timelytask.model.deadline

import com.github.nscala_time.time.Imports.DateTime
// The 'equalsWithComparison' extension is defined in your project, so we assume it's available.
// If not, the test for `equals` can be adapted.
import me.timelytask.util.extensions.equalsWithComparison
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class DeadlineSpec extends AnyWordSpec with Matchers {

  "A Deadline" when {

    "testing completion status" should {

      "return true for isCompleted if completionDate is defined" in {
        // Arrange
        val completedDeadline = Deadline(date = DateTime.now().plusDays(1), initialDate = None, completionDate = Some(DateTime.now()))

        // Act & Assert
        completedDeadline.isCompleted shouldBe true
      }

      "return false for isCompleted if completionDate is None" in {
        // Arrange
        val incompleteDeadline = Deadline(date = DateTime.now().plusDays(1), initialDate = None, completionDate = None)

        // Act & Assert
        incompleteDeadline.isCompleted shouldBe false
      }
    }

    "testing if it is overdue" should {

      "return true for isOverdue if the deadline is in the past and not completed" in {
        // Arrange: A deadline set to one day ago.
        val overdueDeadline = Deadline(date = DateTime.now().minusDays(1), initialDate = None, completionDate = None)

        // Act & Assert
        overdueDeadline.isOverdue shouldBe true
      }

      "return false for isOverdue if the deadline is in the future and not completed" in {
        // Arrange: A deadline set to one day in the future.
        val futureDeadline = Deadline(date = DateTime.now().plusDays(1), initialDate = None, completionDate = None)

        // Act & Assert
        futureDeadline.isOverdue shouldBe false
      }

      "return false for isOverdue if the deadline is in the past but IS completed" in {
        // Arrange: A deadline set to one day ago, but marked as completed now.
        val completedPastDeadline = Deadline(date = DateTime.now().minusDays(1), initialDate = None, completionDate = Some(DateTime.now()))

        // Act & Assert
        completedPastDeadline.isOverdue shouldBe false
      }
    }

    "being compared for equality" should {

      "return false when compared to an object of a different type" in {
        // Arrange
        val deadline = Deadline(date = DateTime.now(), initialDate = None, completionDate = None)

        // Act & Assert: This explicitly tests the `case _ => false` branch.
        deadline.equals("some random string") shouldBe false
        deadline.equals(42) shouldBe false
      }

      "return false when compared to null" in {
        // Arrange
        val deadline = Deadline(date = DateTime.now(), initialDate = None, completionDate = None)

        // Act & Assert: This also tests the `case _ => false` branch.
        deadline.equals(null) shouldBe false
      }
    }
  }
}