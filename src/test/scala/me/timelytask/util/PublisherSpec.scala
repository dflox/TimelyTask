package me.timelytask.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.{never, times, verify}
import org.mockito.ArgumentMatchers.any

/**
 * Test specification for any class that implements the Publisher trait.
 */
class PublisherSpec extends AnyWordSpec with Matchers with MockitoSugar {

  private class SimplePublisher[T] extends Publisher[T] {
    private var currentValue: Option[T] = None
    private var listeners: Vector[(Option[T] => Unit, Option[Any])] = Vector.empty

    override def addListener(listener: Option[T] => Unit, source: Option[Any] = None): Unit = {
      listeners = listeners :+ (listener, source)
    }

    override def update(newValue: Option[T], source: Option[Any] = None): Unit = {
      currentValue = newValue
      listeners.foreach { case (listener, listenerSource) =>
        if (source.isEmpty || source != listenerSource) {
          listener(newValue)
        }
      }
    }

    override def getValue: Option[T] = currentValue
  }

  trait Fixture {
    val publisher: Publisher[String] = new SimplePublisher[String]
  }

  "A Publisher" should {

    "start with an empty value" in new Fixture {
      publisher.getValue shouldBe None
    }

    "update its value and notify a simple listener" in new Fixture {
      val mockListener = mock[Option[String] => Unit]
      publisher.addListener(mockListener)
      val newValue = Some("Hello, World!")

      publisher.update(newValue)

      publisher.getValue shouldBe newValue
      verify(mockListener, times(1)).apply(newValue)
    }

    "notify a listener that has a source if the update comes from NO source" in new Fixture {
      // Arrange
      val listenerSource = Some("ComponentA")
      val listener = mock[Option[String] => Unit]
      publisher.addListener(listener, source = listenerSource)
      val newValue = Some("Broadcast to all")

      // Act
      publisher.update(newValue, source = None)

      // Assert
      // `source.isEmpty` == true
      verify(listener, times(1)).apply(newValue)
    }

    "notify multiple listeners" in new Fixture {
      val listener1 = mock[Option[String] => Unit]
      val listener2 = mock[Option[String] => Unit]
      publisher.addListener(listener1)
      publisher.addListener(listener2)
      val newValue = Some("Update All")

      publisher.update(newValue)

      verify(listener1, times(1)).apply(newValue)
      verify(listener2, times(1)).apply(newValue)
    }

    "NOT notify a listener that comes from the same source as the update" in new Fixture {
      val sourceId = Some("ComponentA")
      val sameSourceListener = mock[Option[String] => Unit]
      publisher.addListener(sameSourceListener, source = sourceId)
      val newValue = Some("This should be muted")

      publisher.update(newValue, source = sourceId)

      publisher.getValue shouldBe newValue
      verify(sameSourceListener, never()).apply(any())
    }

    "notify a listener if the update comes from a DIFFERENT source" in new Fixture {
      val listenerSource = Some("ComponentA")
      val updateSource = Some("ComponentB")
      val listener = mock[Option[String] => Unit]
      publisher.addListener(listener, source = listenerSource)
      val newValue = Some("Crosstalk")

      publisher.update(newValue, source = updateSource)

      verify(listener, times(1)).apply(newValue)
    }

    "correctly handle a mix of sourced and non-sourced listeners" in new Fixture {
      val sourceA = Some("A")
      val sourceB = Some("B")
      val listenerFromA = mock[Option[String] => Unit]
      val listenerFromB = mock[Option[String] => Unit]
      val genericListener = mock[Option[String] => Unit]
      publisher.addListener(listenerFromA, source = sourceA)
      publisher.addListener(listenerFromB, source = sourceB)
      publisher.addListener(genericListener)
      val newValue = Some("Update from A")

      publisher.update(newValue, source = sourceA)

      verify(listenerFromA, never()).apply(any())
      verify(listenerFromB, times(1)).apply(newValue)
      verify(genericListener, times(1)).apply(newValue)
    }
  }
}