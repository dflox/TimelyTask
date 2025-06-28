// src/test/scala/me/timelytask/serviceLayer/servicelayerImpl/ModelServiceImplSpec.scala

package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.Model
import me.timelytask.model.config.Config
import me.timelytask.model.priority.Priority
import me.timelytask.model.state.TaskState
import me.timelytask.model.tag.Tag
import me.timelytask.model.task.Task
import me.timelytask.model.user.User
import me.timelytask.serviceLayer.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.{times, verify, when}
import org.mockito.ArgumentMatchers.{any, eq as eqTo}

import scala.collection.immutable.HashSet

class ModelServiceImplSpec extends AnyWordSpec with Matchers with MockitoSugar {

  // A comprehensive Fixture to mock all dependencies of ModelServiceImpl
  trait Fixture {
    // 1. Mock all the individual services that will be called
    val mockUpdateService: UpdateService = mock[UpdateService]
    val mockTaskService: TaskService = mock[TaskService]
    val mockTagService: TagService = mock[TagService]
    val mockPriorityService: PriorityService = mock[PriorityService]
    val mockTaskStateService: TaskStateService = mock[TaskStateService]
    val mockConfigService: ConfigService = mock[ConfigService]
    val mockUserService: UserService = mock[UserService]

    // 2. Mock the top-level ServiceModule
    val mockServiceModule: ServiceModule = mock[ServiceModule]

    // 3. Wire all the individual mock services to the mock ServiceModule
    when(mockServiceModule.updateService).thenReturn(mockUpdateService)
    when(mockServiceModule.taskService).thenReturn(mockTaskService)
    when(mockServiceModule.tagService).thenReturn(mockTagService)
    when(mockServiceModule.priorityService).thenReturn(mockPriorityService)
    when(mockServiceModule.taskStateService).thenReturn(mockTaskStateService)
    when(mockServiceModule.configService).thenReturn(mockConfigService)
    when(mockServiceModule.userService).thenReturn(mockUserService)

    // 4. Instantiate the class we are testing, injecting our mock module
    val modelService: ModelService = new ModelServiceImpl(mockServiceModule)

    // 5. Create mock data to be returned by our services
    val testUserName = "test-user"
    val mockTask = mock[Task]
    val mockTag = mock[Tag]
    val mockPriority = mock[Priority]
    val mockState = mock[TaskState]
    val mockConfig = mock[Config]
    val mockUser = mock[User]
  }

  "ModelServiceImpl" should {

    // Testing the package-private 'getModel' method
    "getModel should assemble a Model by calling all relevant services" in new Fixture {
      // Arrange: Program the mock services to return our mock data
      when(mockTaskService.loadAllTasks(testUserName)).thenReturn(List(mockTask))
      when(mockTagService.getTags(testUserName)).thenReturn(List(mockTag))
      when(mockPriorityService.getAllPriorities(testUserName)).thenReturn(List(mockPriority))
      when(mockTaskStateService.getAllTaskStates(testUserName)).thenReturn(List(mockState))
      when(mockConfigService.getConfig(testUserName)).thenReturn(mockConfig)
      when(mockUserService.getUser(testUserName)).thenReturn(mockUser)

      // Action: Call the method under test
      // We can call getModel because the test class is in the same package hierarchy
      val resultModel = modelService.asInstanceOf[ModelServiceImpl].getModel(testUserName)

      // Assert: Check that the resulting Model contains the data from our mocks
      resultModel.tasks should be(List(mockTask))
      resultModel.tags should be(HashSet(mockTag))
      resultModel.priorities should be(HashSet(mockPriority))
      resultModel.states should be(HashSet(mockState))
      resultModel.config should be(mockConfig)
      resultModel.user should be(mockUser)
    }

    // Testing the public 'loadModel' method
    "loadModel should get the fully assembled model and pass it to the update service" in new Fixture {
      // Arrange: We need to set up the same mocks as the getModel test
      when(mockTaskService.loadAllTasks(testUserName)).thenReturn(List(mockTask))
      when(mockTagService.getTags(testUserName)).thenReturn(List(mockTag))
      // ... and so on for all other services needed by getModel
      when(mockPriorityService.getAllPriorities(testUserName)).thenReturn(List(mockPriority))
      when(mockTaskStateService.getAllTaskStates(testUserName)).thenReturn(List(mockState))
      when(mockConfigService.getConfig(testUserName)).thenReturn(mockConfig)
      when(mockUserService.getUser(testUserName)).thenReturn(mockUser)

      // Action
      modelService.loadModel(testUserName)

      // Assert: Verify that updateService.updateModel was called
      // We construct the expected model to ensure the correct data was passed
      val expectedModel = Model(
        tasks = List(mockTask),
        tags = HashSet(mockTag),
        priorities = HashSet(mockPriority),
        states = HashSet(mockState),
        config = mockConfig,
        user = mockUser
      )
      verify(mockUpdateService, times(1)).updateModel(eqTo(testUserName), eqTo(expectedModel))
    }

    // Testing the public 'saveModel' method
    "saveModel should decompose the model and call the appropriate save/add method for each part" in new Fixture {
      // Arrange: Create a mock model and program its getters to return our test data
      val modelToSave = mock[Model]
      when(modelToSave.tasks).thenReturn(List(mockTask))
      when(modelToSave.tags).thenReturn(HashSet(mockTag))
      when(modelToSave.priorities).thenReturn(HashSet(mockPriority))
      when(modelToSave.states).thenReturn(HashSet(mockState))
      when(modelToSave.config).thenReturn(mockConfig)

      // Action
      modelService.saveModel(testUserName, modelToSave)

      // Assert: Verify that every relevant service method was called exactly once with the correct data
      verify(mockUpdateService, times(1)).updateModel(testUserName, modelToSave)
      verify(mockUserService, times(1)).addUser(testUserName)
      verify(mockTagService, times(1)).addTag(testUserName, mockTag)
      verify(mockPriorityService, times(1)).addPriority(testUserName, mockPriority)
      verify(mockTaskStateService, times(1)).addTaskState(testUserName, mockState)
      verify(mockTaskService, times(1)).newTask(testUserName, mockTask)
      verify(mockConfigService, times(1)).addConfig(testUserName, mockConfig)
    }
  }
}