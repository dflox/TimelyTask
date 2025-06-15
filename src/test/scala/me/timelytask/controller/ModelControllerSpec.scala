package me.timelytask.controller

import com.softwaremill.macwire.wire
import me.timelytask.core.CoreModuleImpl
import me.timelytask.model.{Model, Task}
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.{timeout, verify}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import scala.jdk.CollectionConverters.*

class ModelControllerSpec extends AnyWordSpec
                          with MockitoSugar {

  "The ModelController" should {

    "be able to add a Task to the Model" in {
      // Setup
      val coreModule = wire[CoreModuleImpl]
      val task = Task.exampleTask
      val callbackHelper = new CallbackHelper[Model]
      callbackHelper.registerCallback(coreModule.registerModelListener)

      // Action
      coreModule.controllers.modelController.addTask(task)

      // Assert
      val model = callbackHelper.getCallbackResults(2)
      model.length shouldBe 1
      model.head.tasks should contain(task)
    }
  }

  "The ModelController" should {

    "be able to remove a Task from the Model" in {
      // Setup
      val coreModule = wire[CoreModuleImpl]
      val task = Task.exampleTask
      coreModule.controllers.modelController.addTask(task)
      val callbackHelper = new CallbackHelper[Model]
      callbackHelper.registerCallback(coreModule.registerModelListener)

      // Action
      coreModule.controllers.modelController.removeTask(task)

      // Assert
      val model = callbackHelper.getCallbackResults(2)
      model.length shouldBe 1
      model.head.tasks should not contain task
    }
  }

    "The ModelController" should {

        "be able to update a Task in the Model" in {
        // Setup
        val coreModule = wire[CoreModuleImpl]
        val task = Task.exampleTask
        coreModule.controllers.modelController.addTask(task)
        val updatedTask = task.copy(name = "Updated Task")
        val callbackHelper = new CallbackHelper[Model]
        callbackHelper.registerCallback(coreModule.registerModelListener)

        // Action
        coreModule.controllers.modelController.updateTask(updatedTask)

        // Assert
        val model = callbackHelper.getCallbackResults(2)
        model.length shouldBe 1
        model.head.tasks should contain(updatedTask)
        }
    }

  class CallbackHelper[ListenerType] {
    private val callback = mock[Option[ListenerType] => Unit]
    def registerCallback(listenerRegistration: (Option[ListenerType] => Unit) => Unit): Unit = {
      listenerRegistration(callback)
    }

    def getCallbackResults(atLeast: Int, timeoutMillis:Int = 1000): Vector[ListenerType] = {
      val captor = ArgumentCaptor.forClass(classOf[Option[ListenerType]])
      verify(callback, timeout(timeoutMillis).atLeast(2)).apply(captor.capture())
      captor.getAllValues.asScala.toVector.filter(_.isDefined).map(_.get)
    }

  }
}
