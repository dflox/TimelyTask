package me.timelytask.model.state

import com.github.nscala_time.time.Imports._
import me.timelytask.model.deadline.Deadline
import me.timelytask.model.task.Task
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.OptionValues
import scalafx.scene.paint.Color

import java.util.UUID
import org.scalatestplus.mockito.MockitoSugar

class OpenStateSpec extends AnyWordSpec with Matchers with MockitoSugar with OptionValues {

  // A fixture to hold all the necessary mock objects and test data.
  trait Fixture {
    // --- Use a REAL Task instance instead of a mock ---
    val initialDeadlineDate: DateTime = new DateTime("2024-08-01T12:00:00Z")
    val initialDeadline: Deadline = Deadline(date = initialDeadlineDate, initialDate = None, completionDate = None)
    val initialTask: Task = Task(
      uuid = UUID.randomUUID(),
      state = Some(UUID.randomUUID()),
      deadline = initialDeadline
    )

    // --- Mock or create real instances for parameters ---
    val targetClosedStateUuid: UUID = UUID.randomUUID()
    val targetDeletedStateUuid: UUID = UUID.randomUUID()

    // We can create real instances of these states since they have no complex logic for this test.
    val targetClosedState = new ClosedState("Closed", "desc", null.asInstanceOf[Color], targetClosedStateUuid)
    val targetDeletedState = new DeletedState("Deleted", "desc", null.asInstanceOf[Color], targetDeletedStateUuid)

    // This is needed for the `start` method test.
    val mockOpenState: OpenState = mock[OpenState]

    // --- Instantiate the class under test ---
    // KEY TRICK: Pass `null` for the Color to bypass JavaFX dependency.
    val openState = new OpenState("Open", "Task is in progress", null.asInstanceOf[Color])
  }

  "An OpenState" should {

    "return None when start is called, as an open task cannot be started again" in new Fixture {
      val result = openState.start(initialTask, mockOpenState)
      result shouldBe None
    }

    "return the task with a new ClosedState when complete is called" in new Fixture {
      // Act
      val resultOption = openState.complete(initialTask, targetClosedState)

      // Assert
      resultOption should be (defined)
      val updatedTask = resultOption.value
      updatedTask.state should contain(targetClosedStateUuid)
      updatedTask.uuid shouldBe initialTask.uuid // Ensure other properties are unchanged
    }

    "return the task with a new DeletedState when delete is called" in new Fixture {
      // Act
      val resultOption = openState.delete(initialTask, targetDeletedState)

      // Assert
      resultOption should be (defined)
      val updatedTask = resultOption.value
      updatedTask.state should contain(targetDeletedStateUuid)
      updatedTask.uuid shouldBe initialTask.uuid
    }

    "return the task with an extended deadline when extendDeadline is called" in new Fixture {
      // Arrange
      val extensionPeriod: Period = 7.days.toPeriod

      // Act
      val resultOption = openState.extendDeadline(initialTask, extensionPeriod)

      // Assert
      resultOption should be (defined)
      val updatedTask = resultOption.value

      // Calculate the expected new deadline
      val expectedDeadline = initialDeadlineDate + extensionPeriod
      updatedTask.deadline.date.getMillis shouldBe expectedDeadline.getMillis

      // Ensure other properties are unchanged
      updatedTask.uuid shouldBe initialTask.uuid
      updatedTask.state shouldBe initialTask.state
    }
  }

  "The OpenState companion object" should {

    "have a correct stateType constant" in {
      OpenState.stateType shouldBe "open"
    }

    "have an apply factory method that creates a new OpenState instance" in {
      val instance = OpenState("Test Name", "Test Desc", null.asInstanceOf[Color])

      instance shouldBe a[OpenState]
      instance.name shouldBe "Test Name"
      instance.description shouldBe "Test Desc"
    }

    "have an apply factory method that accepts a UUID" in {
      val specificUuid = UUID.randomUUID()
      val instance = OpenState("Test Name", "Test Desc", null.asInstanceOf[Color], specificUuid)

      instance.uuid shouldBe specificUuid
    }
  }
}