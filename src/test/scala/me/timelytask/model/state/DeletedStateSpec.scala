package me.timelytask.model.state

import com.github.nscala_time.time.Imports._
import me.timelytask.model.task.Task
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.OptionValues
import scalafx.scene.paint.Color

import java.util.UUID
import org.scalatestplus.mockito.MockitoSugar

class DeletedStateSpec extends AnyWordSpec with Matchers with MockitoSugar with OptionValues {

  // A fixture to hold all the necessary mock objects and test data.
  trait Fixture {
    val initialTask: Task = Task(uuid = UUID.randomUUID(), state = Some(UUID.randomUUID()))

    val mockClosedState: ClosedState = mock[ClosedState]

    val openStateUuid: UUID = UUID.randomUUID()
    val anotherDeletedStateUuid: UUID = UUID.randomUUID()

    val targetOpenState = new OpenState("Open", "desc", null.asInstanceOf[Color], openStateUuid)
    val targetDeletedState = new DeletedState("Also Deleted", "desc", null.asInstanceOf[Color], anotherDeletedStateUuid)

    val deletedState = new DeletedState("Deleted", "Task has been removed", null.asInstanceOf[Color])
  }

  "A DeletedState" should {

    "return None when complete is called, as a deleted task cannot be completed" in new Fixture {
      val result = deletedState.complete(initialTask, mockClosedState)
      result shouldBe None
    }

    "return None when extendDeadline is called, as a deleted task's deadline cannot be extended" in new Fixture {
      val result = deletedState.extendDeadline(initialTask, 1.day.toPeriod)
      result shouldBe None
    }

    "return the task with an updated state when start is called (restoring the task)" in new Fixture {
      // Act: Call the 'start' method to transition the task to an open state.
      val resultOption = deletedState.start(initialTask, targetOpenState)

      // Assert: Check that a task was returned.
      resultOption should be (defined)
      val updatedTask = resultOption.value

      // Assert: The new state of the task should be the UUID of the targetOpenState.
      updatedTask.state should contain(openStateUuid)

      // Assert: Other properties of the task should remain unchanged.
      updatedTask.uuid shouldBe initialTask.uuid
    }

    "return the task with an updated state when delete is called (re-applying the deleted state)" in new Fixture {
      // Act: Call the 'delete' method.
      val resultOption = deletedState.delete(initialTask, targetDeletedState)

      // Assert: Check that a task was returned.
      resultOption should be (defined)
      val updatedTask = resultOption.value

      // Assert: The new state should be the UUID of the *new* deleted state passed in.
      updatedTask.state should contain(anotherDeletedStateUuid)

      // Assert: Other properties should be unchanged.
      updatedTask.uuid shouldBe initialTask.uuid
    }
  }

  "The DeletedState companion object" should {

    "have a correct stateType constant" in {
      DeletedState.stateType shouldBe "deleted"
    }

    "have an apply factory method that creates a new DeletedState instance" in {
      // Use the same `null` trick to avoid JavaFX dependency.
      val instance = DeletedState("Test Name", "Test Desc", null.asInstanceOf[Color])

      instance shouldBe a[DeletedState]
      instance.name shouldBe "Test Name"
      instance.description shouldBe "Test Desc"
    }

    "have an apply factory method that accepts a UUID" in {
      val specificUuid = UUID.randomUUID()
      val instance = DeletedState("Test Name", "Test Desc", null.asInstanceOf[Color], specificUuid)

      instance.uuid shouldBe specificUuid
    }
  }
}