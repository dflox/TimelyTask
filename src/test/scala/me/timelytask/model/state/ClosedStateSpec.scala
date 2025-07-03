package me.timelytask.model.state

import com.github.nscala_time.time.Imports._
import me.timelytask.model.task.Task
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.OptionValues
import scalafx.scene.paint.Color

import java.util.UUID
import org.scalatestplus.mockito.MockitoSugar

class ClosedStateSpec extends AnyWordSpec with Matchers with MockitoSugar with OptionValues {

  trait Fixture {
    val initialTask: Task = Task(uuid = UUID.randomUUID(), state = Some(UUID.randomUUID()))

    val mockOpenState: OpenState = mock[OpenState]
    val mockDeletedState: DeletedState = mock[DeletedState]

    val closedStateUuid: UUID = UUID.randomUUID()
    val anotherClosedStateUuid: UUID = UUID.randomUUID()

    val closedState = new ClosedState("Closed", "Task is finished", null.asInstanceOf[Color], closedStateUuid)

    val anotherClosedState = new ClosedState("Also Closed", "Desc", null.asInstanceOf[Color], anotherClosedStateUuid)
  }

  "A ClosedState" should {

    "return None when start is called, as a closed task cannot be started" in new Fixture {
      val result = closedState.start(initialTask, mockOpenState)
      result shouldBe None
    }

    "return None when delete is called, as a closed task cannot be deleted" in new Fixture {
      val result = closedState.delete(initialTask, mockDeletedState)
      result shouldBe None
    }

    "return None when extendDeadline is called, as a closed task's deadline cannot be extended" in new Fixture {
      val result = closedState.extendDeadline(initialTask, 1.day.toPeriod)
      result shouldBe None
    }

    "return the task with an updated state when complete is called" in new Fixture {
      // Act: Call the method under test.
      val resultOption = closedState.complete(initialTask, anotherClosedState)

      // Assert: Check that the result is a Some[Task]
      resultOption should be (defined)
      val updatedTask = resultOption.value

      // Assert: Check that the returned task has the correct, updated state UUID.
      updatedTask.state should contain(anotherClosedStateUuid)

      // Assert: Check that other properties of the task have not changed.
      updatedTask.uuid shouldBe initialTask.uuid
      updatedTask.name shouldBe initialTask.name
    }
  }

  "The ClosedState companion object" should {

    "have a correct stateType constant" in {
      ClosedState.stateType shouldBe "closed"
    }

    "have an apply factory method that creates a new ClosedState instance" in {
      val instance = ClosedState("Test Name", "Test Desc", null.asInstanceOf[Color])

      instance shouldBe a[ClosedState]
      instance.name shouldBe "Test Name"
      instance.description shouldBe "Test Desc"
    }

    "have an apply factory method that accepts a UUID" in {
      val specificUuid = UUID.randomUUID()
      val instance = ClosedState("Test Name", "Test Desc", null.asInstanceOf[Color], specificUuid)

      instance.uuid shouldBe specificUuid
    }
  }
}