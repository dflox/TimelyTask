package me.timelytask.controller

import me.timelytask.core.CoreModuleImpl
import me.timelytask.model.Model
import me.timelytask.model.task.Task
import me.timelytask.model.user.User
import me.timelytask.repository.{ TaskRepository, UserRepository }
import me.timelytask.serviceLayer.ServiceModule
import me.timelytask.testUtil.ServiceModuleBuilder
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.*
import org.mockito.{ ArgumentCaptor, ArgumentMatchers }
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

import java.util.UUID
import scala.jdk.CollectionConverters.*

class ModelControllerSpec extends AnyWordSpec with MockitoSugar {

  "The ModelController" should {

    "be able to add a Task to the Model" in {
      // Setup
      val userName = "testUser"
      val task = Task.exampleTask
      val serviceModule: ServiceModule = ServiceModuleBuilder()
        .withTaskRepository(setupTaskRepository("testUser", task))
        .withUserRepository(setupUserRepository("testUser"))
        .build()
      val coreModule = CoreModuleImpl(serviceModule)
      coreModule.controllers.updateController.init()
      val callbackHelper = new CallbackHelper[Model]
      callbackHelper.registerCallback(
        coreModule.registerModelListener,
        userName
      )

      // Action
      coreModule.controllers.modelController.addTask(userName, task)

      // Assert
      val model = callbackHelper.getCallbackResults(2)
      model.length shouldBe 1
      model.head.tasks should contain(task)
    }
  }

//  "The ModelController" should {
//
//    "be able to remove a Task from the Model" in {
//      // Setup
//      val coreModule = wire[CoreModuleImpl]
//      val task = Task.exampleTask
//      coreModule.controllers.modelController.addTask(task)
//      val callbackHelper = new CallbackHelper[Model]
//      callbackHelper.registerCallback(coreModule.registerModelListener)
//
//      // Action
//      coreModule.controllers.modelController.removeTask(task)
//
//      // Assert
//      val model = callbackHelper.getCallbackResults(2)
//      model.length shouldBe 1
//      model.head.tasks should not contain task
//    }
//  }

//    "The ModelController" should {
//
//        "be able to update a Task in the Model" in {
//        // Setup
//        val coreModule = wire[CoreModuleImpl]
//        val task = Task.exampleTask
//        coreModule.controllers.modelController.addTask(task)
//        val updatedTask = task.copy(name = "Updated Task")
//        val callbackHelper = new CallbackHelper[Model]
//        callbackHelper.registerCallback(coreModule.registerModelListener)
//
//        // Action
//        coreModule.controllers.modelController.updateTask(updatedTask)
//
//        // Assert
//        val model = callbackHelper.getCallbackResults(2)
//        model.length shouldBe 1
//        model.head.tasks should contain(updatedTask)
//        }
//    }

  class CallbackHelper[ListenerType] {
    private val callback = mock[Option[ListenerType] => Unit]

    def registerCallback(
        listenerRegistration: (Option[ListenerType] => Unit, String) => Unit,
        target: String
      ): Unit = {
      listenerRegistration(callback, target)
    }

    def getCallbackResults(atLeast: Int, timeoutMillis: Int = 1000): Vector[ListenerType] = {
      val captor = ArgumentCaptor.forClass(classOf[Option[ListenerType]])
      verify(callback, timeout(timeoutMillis).atLeast(2))
        .apply(captor.capture())
      captor.getAllValues.asScala.toVector.filter(_.isDefined).map(_.get)
    }
  }

  def setupTaskRepository(
      userName: String,
      taskToBeAdded: Task
    ): TaskRepository = {
    val taskRepository = mock[TaskRepository]
    when(
      taskRepository.addTask(
        ArgumentMatchers.eq(userName),
        ArgumentMatchers.eq(taskToBeAdded)
      )
    ).thenAnswer( inv => ())
    when(
      taskRepository.getTaskById(ArgumentMatchers.eq(userName), any[UUID]())
    ).thenAnswer { invocation =>
      val taskId = invocation.getArgument[UUID](1)
      if (taskId == taskToBeAdded.uuid) {
        taskToBeAdded
      } else {
        throw new NoSuchElementException(s"Task with ID $taskId not found")
      }
    }
    when(taskRepository.getAllTasks(ArgumentMatchers.eq(userName))).thenReturn(
      Vector(taskToBeAdded)
    )
    taskRepository
  }

  def setupUserRepository(userName: String): UserRepository = {
    val userRepository = mock[UserRepository]
    when(userRepository.getUser(ArgumentMatchers.eq(userName)))
      .thenReturn(User(userName))
    when(userRepository.userExists(ArgumentMatchers.eq(userName)))
      .thenReturn(true)
    userRepository
  }
}
