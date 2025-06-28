// src/test/scala/me/timelytask/serviceLayer/servicelayerImpl/TaskServiceImplSpec.scala

package me.timelytask.serviceLayer.servicelayerImpl

import com.github.nscala_time.time.Imports.{DateTime, Period}
import me.timelytask.model.deadline.Deadline
import me.timelytask.model.task.Task
import me.timelytask.repository.TaskRepository
import me.timelytask.serviceLayer.{ModelService, ServiceModule, TaskService, UpdateService}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.{times, verify, when}
import org.mockito.ArgumentMatchers.{any, eq as eqTo}

import java.util.UUID
import scala.collection.immutable.HashSet

class TaskServiceImplSpec extends AnyWordSpec with Matchers with MockitoSugar {

  // A comprehensive Fixture to mock all dependencies and set up data.
  trait Fixture {
    val mockTaskRepository: TaskRepository = mock[TaskRepository]
    val mockServiceModule: ServiceModule = mock[ServiceModule]
    val mockUpdateService: UpdateService = mock[UpdateService]
    val mockModelService: ModelService = mock[ModelService]
    when(mockServiceModule.updateService).thenReturn(mockUpdateService)
    when(mockServiceModule.modelService).thenReturn(mockModelService)

    val taskService: TaskService = new TaskServiceImpl(mockServiceModule, mockTaskRepository)

    val testUserName = "test-user"
    val testTaskUUID: UUID = UUID.randomUUID()

    // Use Task.exampleTask as a template to create a predictable test task.
    val testTask: Task = Task.exampleTask.copy(
      uuid = testTaskUUID,
      priority = None,
      tags = HashSet.empty,
      dependentOn = HashSet.empty,
      reoccurring = false,
      scheduleDate = DateTime.now() // Explicitly set a non-optional DateTime
    )

    val genericMockTask: Task = mock[Task]
  }

  "TaskServiceImpl" should {

    "newTask should add the task to the repository and notify the update service" in new Fixture {
      taskService.newTask(testUserName, genericMockTask)
      verify(mockTaskRepository, times(1)).addTask(testUserName, genericMockTask)
      verify(mockUpdateService, times(1)).updateTask(testUserName, genericMockTask)
    }

    "deleteTask should delete from the repository and trigger a model reload" in new Fixture {
      taskService.deleteTask(testUserName, testTaskUUID)
      verify(mockTaskRepository, times(1)).deleteTask(testUserName, testTaskUUID)
      verify(mockModelService, times(1)).loadModel(testUserName)
    }

    // --- All update tests now follow the AnyWordSpec structure ---

    "updateScheduleDate should save the task with the new date" in new Fixture {
      val newDate = DateTime.now().plusDays(5)
      val expectedTask = testTask.copy(scheduleDate = newDate)
      when(mockTaskRepository.getTaskById(testUserName, testTaskUUID)).thenReturn(testTask)

      taskService.updateScheduleDate(testUserName, testTaskUUID, newDate)

      verify(mockTaskRepository, times(1)).updateTask(testUserName, testTaskUUID, expectedTask)
      verify(mockUpdateService, times(1)).updateTask(testUserName, expectedTask)
    }

    "updateState should save the task with the new state" in new Fixture {
      val newState = Some(UUID.randomUUID())
      val expectedTask = testTask.copy(state = newState)
      when(mockTaskRepository.getTaskById(testUserName, testTaskUUID)).thenReturn(testTask)

      taskService.updateState(testUserName, testTaskUUID, newState.get)

      verify(mockTaskRepository, times(1)).updateTask(testUserName, testTaskUUID, expectedTask)
      verify(mockUpdateService, times(1)).updateTask(testUserName, expectedTask)
    }

    "updatePriority should save the task with the new priority" in new Fixture {
      val newPriority = Some(UUID.randomUUID())
      val expectedTask = testTask.copy(priority = newPriority)
      when(mockTaskRepository.getTaskById(testUserName, testTaskUUID)).thenReturn(testTask)

      taskService.updatePriority(testUserName, testTaskUUID, newPriority.get)

      verify(mockTaskRepository, times(1)).updateTask(testUserName, testTaskUUID, expectedTask)
      verify(mockUpdateService, times(1)).updateTask(testUserName, expectedTask)
    }

    "updateDeadline should save the task with the new deadline" in new Fixture {
      // Create a mock Deadline, which is not an Option
      val newDeadline: Deadline = mock[Deadline]
      // Pass the mock Deadline directly to copy()
      val expectedTask = testTask.copy(deadline = newDeadline)
      when(mockTaskRepository.getTaskById(testUserName, testTaskUUID)).thenReturn(testTask)

      // The service method expects a non-optional Deadline
      taskService.updateDeadline(testUserName, testTaskUUID, newDeadline)

      verify(mockTaskRepository, times(1)).updateTask(testUserName, testTaskUUID, expectedTask)
      verify(mockUpdateService, times(1)).updateTask(testUserName, expectedTask)
    }

    "updateTedDuration should save the task with the new TED duration" in new Fixture {
      val newDuration = Period.days(5)
      val expectedTask = testTask.copy(tedDuration = newDuration)
      when(mockTaskRepository.getTaskById(testUserName, testTaskUUID)).thenReturn(testTask)

      taskService.updateTedDuration(testUserName, testTaskUUID, newDuration)

      verify(mockTaskRepository, times(1)).updateTask(testUserName, testTaskUUID, expectedTask)
      verify(mockUpdateService, times(1)).updateTask(testUserName, expectedTask)
    }

    "addDependentTask should save the task with the new dependency" in new Fixture {
      val dependentUuid = UUID.randomUUID()
      val expectedTask = testTask.copy(dependentOn = HashSet(dependentUuid))
      when(mockTaskRepository.getTaskById(testUserName, testTaskUUID)).thenReturn(testTask)

      taskService.addDependentTask(testUserName, testTaskUUID, dependentUuid)

      verify(mockTaskRepository, times(1)).updateTask(testUserName, testTaskUUID, expectedTask)
      verify(mockUpdateService, times(1)).updateTask(testUserName, expectedTask)
    }

    "removeDependentTask should save the task with the dependency removed" in new Fixture {
      val dependentUuid = UUID.randomUUID()
      val taskWithDependency = testTask.copy(dependentOn = HashSet(dependentUuid))
      val expectedTask = taskWithDependency.copy(dependentOn = HashSet.empty)
      when(mockTaskRepository.getTaskById(testUserName, testTaskUUID)).thenReturn(taskWithDependency)

      taskService.removeDependentTask(testUserName, testTaskUUID, dependentUuid)

      verify(mockTaskRepository, times(1)).updateTask(testUserName, testTaskUUID, expectedTask)
      verify(mockUpdateService, times(1)).updateTask(testUserName, expectedTask)
    }

    "updateReoccurring should save the task with the new reoccurring flag" in new Fixture {
      val expectedTask = testTask.copy(reoccurring = true)
      when(mockTaskRepository.getTaskById(testUserName, testTaskUUID)).thenReturn(testTask)

      taskService.updateReoccurring(testUserName, testTaskUUID, true)

      verify(mockTaskRepository, times(1)).updateTask(testUserName, testTaskUUID, expectedTask)
      verify(mockUpdateService, times(1)).updateTask(testUserName, expectedTask)
    }

    "updateRecurrenceInterval should save the task with the new interval" in new Fixture {
      val newInterval = Period.weeks(1)
      val expectedTask = testTask.copy(recurrenceInterval = newInterval)
      when(mockTaskRepository.getTaskById(testUserName, testTaskUUID)).thenReturn(testTask)

      taskService.updateRecurrenceInterval(testUserName, testTaskUUID, newInterval)

      verify(mockTaskRepository, times(1)).updateTask(testUserName, testTaskUUID, expectedTask)
      verify(mockUpdateService, times(1)).updateTask(testUserName, expectedTask)
    }

    "updateRealDuration should save the task with the new real duration" in new Fixture {
      val newDuration = Some(Period.hours(2))
      val expectedTask = testTask.copy(realDuration = newDuration)
      when(mockTaskRepository.getTaskById(testUserName, testTaskUUID)).thenReturn(testTask)

      taskService.updateRealDuration(testUserName, testTaskUUID, newDuration)

      verify(mockTaskRepository, times(1)).updateTask(testUserName, testTaskUUID, expectedTask)
      verify(mockUpdateService, times(1)).updateTask(testUserName, expectedTask)
    }

    "loadAllTasks should delegate directly to the repository" in new Fixture {
      val expectedTasks = List(genericMockTask)
      when(mockTaskRepository.getAllTasks(testUserName)).thenReturn(expectedTasks)
      val result = taskService.asInstanceOf[TaskServiceImpl].loadAllTasks(testUserName)
      result should be(expectedTasks)
      verify(mockTaskRepository, times(1)).getAllTasks(testUserName)
    }
  }
}