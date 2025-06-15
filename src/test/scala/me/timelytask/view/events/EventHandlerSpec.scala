package me.timelytask.view.events.handler

import me.timelytask.view.events.event.Event
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito
import org.mockito.Mockito.{never, timeout, verify}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

class EventHandlerImplSpec extends AnyWordSpec
  with MockitoSugar
  with Matchers
  with BeforeAndAfterEach {

  private var eventHandler: EventHandlerImpl = _

  override def beforeEach(): Unit = {
    eventHandler = new EventHandlerImpl()
  }

  override def afterEach(): Unit = {
    if (eventHandler != null && !eventHandler.runner.isCompleted) {
      eventHandler.shutdown()
    }
  }

  "The EventHandlerImpl" should {

    "process a single event asynchronously" in {
      val mockEvent = mock[Event[?]]
      eventHandler.handle(mockEvent)
      verify(mockEvent, timeout(1000)).call
    }

    "process multiple events in the correct order" in {
      // Setup
      val mockEvent1 = mock[Event[?]]
      val mockEvent2 = mock[Event[?]]
      val mockEvent3 = mock[Event[?]]

      val inOrderVerifier = Mockito.inOrder(mockEvent1, mockEvent2, mockEvent3)

      // Action
      eventHandler.handle(mockEvent1)
      eventHandler.handle(mockEvent2)
      eventHandler.handle(mockEvent3)

      // Assert
      inOrderVerifier.verify(mockEvent1, timeout(1000)).call
      inOrderVerifier.verify(mockEvent2, timeout(1000)).call
      inOrderVerifier.verify(mockEvent3, timeout(1000)).call
    }

    "process remaining events on shutdown and then stop" in {
      val shutdownTestHandler = new EventHandlerImpl()
      val eventInQueue = mock[Event[?]]
      val eventAfterShutdown = mock[Event[?]]

      shutdownTestHandler.handle(eventInQueue)
      shutdownTestHandler.shutdown()
      shutdownTestHandler.handle(eventAfterShutdown)

      verify(eventInQueue, timeout(1000)).call
      Thread.sleep(250) // Give a moment for any stray processing to fail
      verify(eventAfterShutdown, never()).call
    }
  }
}