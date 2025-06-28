package me.timelytask.controller

import com.softwaremill.macwire.wire
import me.timelytask.core.CoreModuleImpl
import me.timelytask.model.Model
import me.timelytask.model.task.Task
import me.timelytask.model.user.User
import me.timelytask.repository.{TaskRepository, UserRepository}
import me.timelytask.serviceLayer.ServiceModule
import me.timelytask.testUtil.ServiceModuleBuilder
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.{timeout, verify, when}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

import java.util.UUID
import scala.collection.mutable
import scala.jdk.CollectionConverters.*

class ModelControllerSpec extends AnyWordSpec with MockitoSugar {

  "The ModelController" should {

    "be able to add a Task to the Model" in {
      // Setup
      val userName = "testUser"
      val serviceModule: ServiceModule = ServiceModuleBuilder()
        .withTaskRepository(setupTaskRepository("testUser"))
        .withUserRepository(setupUserRepository("testUser"))
        .build()
      val coreModule = CoreModuleImpl(serviceModule)
      val task = Task.exampleTask
      val callbackHelper = new CallbackHelper[Model]
      callbackHelper.registerCallback(coreModule.registerModelListener, userName)

      // Action
      coreModule.controllers.modelController.addTask(userName, task)

      // Assert
      val model = callbackHelper.getCallbackResults(2)
      model.length shouldBe 1
      model.head.tasks should contain(task)
    }
  }

  class CallbackHelper[ListenerType] {
    private val callback = mock[Option[ListenerType] => Unit]

    def registerCallback(
        listenerRegistration: (Option[ListenerType] => Unit, String) => Unit,
        target: String
      ): Unit = {
      listenerRegistration(callback, target)
    }

    def getCallbackResults(atLeast: Int, timeoutMillis: Int = 200): Vector[ListenerType] = {
      val captor = ArgumentCaptor.forClass(classOf[Option[ListenerType]])
      verify(callback, timeout(timeoutMillis).atLeast(2)).apply(captor.capture())
        captor.getAllValues.asScala.toVector.filter(_.isDefined).map(_.get)
    }
  }

  def setupTaskRepository(userName: String): TaskRepository = {
    val virtualTaskList = mutable.HashSet[Task]()
    val taskRepository = mock[TaskRepository]
    when(taskRepository.addTask(userName, _)).thenAnswer { invocation =>
      val task = invocation.getArgument[Task](1)
      virtualTaskList.addOne(task)
    }
    when(taskRepository.getTaskById(userName, _)).thenAnswer { invocation =>
      val taskUUID = invocation.getArgument[UUID](1)
      virtualTaskList.find(_.uuid == taskUUID).getOrElse(Task.exampleTask)
    }
    when(taskRepository.getAllTasks).thenReturn(virtualTaskList.toVector)
    when(taskRepository.deleteTask(userName, _)).thenAnswer { invocation =>
      val taskUUID = invocation.getArgument[UUID](1)
      virtualTaskList.filterInPlace( task => task.uuid != taskUUID )
    }
    taskRepository
  }

  def setupUserRepository(userName: String): UserRepository = {
    val userRepository = mock[UserRepository]
    when(userRepository.getUser(userName)).thenReturn(Some(User(userName)))
    when(userRepository.userExists(userName)).thenReturn(true)
    userRepository
  }
}
