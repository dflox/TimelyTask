package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.Model
import me.timelytask.model.config.Config
import me.timelytask.model.priority.Priority
import me.timelytask.model.state.TaskState
import me.timelytask.model.tag.Tag
import me.timelytask.serviceLayer.UpdateService
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.{times, verify, never}
import org.mockito.ArgumentMatchers.{any, eq as eqTo}

class UpdateServiceImplSpec extends AnyWordSpec with Matchers with MockitoSugar {

  // A Fixture to set up the service and mock data for each test.
  trait Fixture {
    // Instantiate the real class we are testing. No dependencies needed.
    val updateService: UpdateService = new UpdateServiceImpl()

    // Helper variables
    val testUserName = "test-user"

    // Mock data objects to pass to the update methods.
    val mockModel: Model = mock[Model]
    val mockTag: Tag = mock[Tag]
    val mockPriority: Priority = mock[Priority]
    val mockTaskState: TaskState = mock[TaskState]
    val mockConfig: Config = mock[Config]
  }

  "UpdateServiceImpl" should {

    "not throw an exception if an update method is called before a listener is registered" in new Fixture {
      // The initial listener is a do-nothing function. This test ensures it's safe.
      noException should be thrownBy {
        updateService.asInstanceOf[UpdateServiceImpl].updateTags(testUserName, mockTag)
      }
    }

    "updateModel should invoke the registered model listener" in new Fixture {
      // Arrange: Create and register a mock listener function.
      val mockListener = mock[(String, Model) => Unit]
      updateService.registerModelUpdateListener(mockListener)

      // Action: Call the update method. We must cast to the implementation to access the package-private method.
      updateService.asInstanceOf[UpdateServiceImpl].updateModel(testUserName, mockModel)

      // Assert: Verify the mock listener was called exactly once with the correct arguments.
      // Mockito mocks a function by mocking its `apply` method.
      verify(mockListener, times(1)).apply(eqTo(testUserName), eqTo(mockModel))
    }

    "updateTags should invoke the registered tag listener" in new Fixture {
      // Arrange
      val mockListener = mock[(String, Tag) => Unit]
      updateService.registerTagUpdateListener(mockListener)

      // Action
      updateService.asInstanceOf[UpdateServiceImpl].updateTags(testUserName, mockTag)

      // Assert
      verify(mockListener, times(1)).apply(eqTo(testUserName), eqTo(mockTag))
    }

    "updatePriorities should invoke the registered priority listener" in new Fixture {
      // Arrange
      val mockListener = mock[(String, Priority) => Unit]
      updateService.registerPriorityUpdateListener(mockListener)

      // Action
      updateService.asInstanceOf[UpdateServiceImpl].updatePriorities(testUserName, mockPriority)

      // Assert
      verify(mockListener, times(1)).apply(eqTo(testUserName), eqTo(mockPriority))
    }

    "updateTaskStates should invoke the registered task state listener" in new Fixture {
      // Arrange
      val mockListener = mock[(String, TaskState) => Unit]
      updateService.registerTaskStateUpdateListener(mockListener)

      // Action
      updateService.asInstanceOf[UpdateServiceImpl].updateTaskStates(testUserName, mockTaskState)

      // Assert
      verify(mockListener, times(1)).apply(eqTo(testUserName), eqTo(mockTaskState))
    }

    "updateConfig should invoke the registered config listener" in new Fixture {
      // Arrange
      val mockListener = mock[(String, Config) => Unit]
      updateService.registerConfigUpdateListener(mockListener)

      // Action
      updateService.asInstanceOf[UpdateServiceImpl].updateConfig(testUserName, mockConfig)

      // Assert
      verify(mockListener, times(1)).apply(eqTo(testUserName), eqTo(mockConfig))
    }
  }
}