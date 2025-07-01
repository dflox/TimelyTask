// src/test/scala/me/timelytask/model/task/TaskSpec.scala

package me.timelytask.model.task

import me.timelytask.model.state.{ClosedState, DeletedState, OpenState, TaskState}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.{times, verify, when}
import org.mockito.ArgumentMatchers.any

import java.util.UUID

class TaskSpec extends AnyWordSpec with Matchers with MockitoSugar {

  // A comprehensive Fixture to set up mock states and a base task for testing.
  trait Fixture {
    // 1. Create predictable UUIDs for our tests
    val taskUuid: UUID = UUID.randomUUID()
    val priorityUuid: UUID = UUID.randomUUID()
    val initialStateUuid: UUID = UUID.randomUUID()
    val openStateUuid: UUID = UUID.randomUUID()
    val closedStateUuid: UUID = UUID.randomUUID()
    val deletedStateUuid: UUID = UUID.randomUUID()

    // 2. Create mock instances of the different states
    val mockInitialState: OpenState = mock[OpenState]
    val mockOpenState: OpenState = mock[OpenState]
    val mockClosedState: ClosedState = mock[ClosedState]
    val mockDeletedState: DeletedState = mock[DeletedState]

    // 3. Create a predictable base task using the exampleTask as a template
    val baseTask: Task = Task.exampleTask.copy(
      uuid = taskUuid,
      priority = Some(priorityUuid),
      state = Some(initialStateUuid)
    )

    // 4. Create a mock of the stateFinder function
    val mockStateFinder: UUID => Option[TaskState] = mock[UUID => Option[TaskState]]

    // 5. Program the mockStateFinder to return our mock states
    when(mockStateFinder.apply(initialStateUuid)).thenReturn(Some(mockInitialState))
    when(mockStateFinder.apply(openStateUuid)).thenReturn(Some(mockOpenState))
    when(mockStateFinder.apply(closedStateUuid)).thenReturn(Some(mockClosedState))
    when(mockStateFinder.apply(deletedStateUuid)).thenReturn(Some(mockDeletedState))
  }

  "A Task" should {

    // --- Testing the 'isValid' method ---
    "be valid when it has a name and priority" in new Fixture {
      baseTask.isValid should be(None)
    }

    "be invalid if its name is empty" in new Fixture {
      val invalidTask = baseTask.copy(name = "")
      invalidTask.isValid should be(Some("Name cannot be empty."))
    }

    "be invalid if its priority is empty" in new Fixture {
      val invalidTask = baseTask.copy(priority = None)
      invalidTask.isValid should be(Some("Please choose a priority for this task."))
    }

    // --- Testing the custom 'equals' method ---
    "be equal to another task with the same UUID" in new Fixture {
      val sameTaskWithDifferentName = baseTask.copy(name = "A different name")
      baseTask.equals(sameTaskWithDifferentName) should be(true)
      baseTask should be(sameTaskWithDifferentName) // Using '==' should also work
    }

    "not be equal to another task with a different UUID" in new Fixture {
      val differentTask = baseTask.copy(uuid = UUID.randomUUID())
      baseTask.equals(differentTask) should be(false)
    }

    "not be equal to an object of a different type" in new Fixture {
      baseTask.equals("a string") should be(false)
    }

    // --- Testing the State Pattern delegation methods ---

    "start method" should {
//      "delegate to the OpenState's start method on success" in new Fixture {
//        // Arrange
//        val updatedTask: Task = mock[Task]
//        // Program the mock state to return the updated task when 'start' is called
//        when(mockInitialState.start(baseTask, mockOpenState)).thenReturn(Some(updatedTask))
//
//        // Action
//        val result: Option[Task] = baseTask.start(mockStateFinder, Some(openStateUuid))
//
//        // Assert
//        result should be(Some(updatedTask))
//        // Verify that the delegation actually happened
//        verify(mockInitialState, times(1)).start(baseTask, mockOpenState)
//      }

      "return None if the current state is not found" in new Fixture {
        val taskWithNoState = baseTask.copy(state = None)
        val result = taskWithNoState.start(mockStateFinder, Some(openStateUuid))
        result should be(None)
      }

      "return None if the target open state is not found" in new Fixture {
        // Here the target openState UUID doesn't have a corresponding state in our finder
        val result = baseTask.start(mockStateFinder, Some(UUID.randomUUID()))
        result should be(None)
      }

      "return None if the current state is not an OpenState" in new Fixture {
        // Simulate that the stateFinder returns a ClosedState instead of an OpenState
        when(mockStateFinder.apply(initialStateUuid)).thenReturn(Some(mockClosedState))

        // Action: The 'start' method expects an OpenState, but gets a ClosedState.
        // The internal 'getState' helper will fail the cast and return None.
        val result = baseTask.start(mockStateFinder, Some(openStateUuid))

        // Assert
        result should be(None)
      }
    }

    "complete method" should {
      "return None because the current state (OpenState) cannot be cast to a ClosedState" in new Fixture {
        // This test verifies the type safety of the `getState` helper method.
        // The `complete` method requires the current state to be a `ClosedState` to proceed.
        // Our fixture sets the current state to be an `OpenState`.
        val result = baseTask.complete(mockStateFinder, Some(closedStateUuid))
        result should be(None)
      }
    }
  }

  "The Task companion object" should {
    "provide a valid exampleTask" in {
      // This is a simple sanity check
      val task = Task.exampleTask
      task shouldBe a[Task]
      task.name should be("Example Task")
      task.isValid should be(None) // The example task should itself be valid
    }
  }
}