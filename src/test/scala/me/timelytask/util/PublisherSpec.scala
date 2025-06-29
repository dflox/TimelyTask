package me.timelytask.util

import me.timelytask.util.publisher.PublisherImpl
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{ never, times, verify }
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

class PublisherSpec extends AnyWordSpec with Matchers with MockitoSugar {

  // Fixture to create a new publisher for each test context
  trait Fixture {
    val publisher: Publisher[String] = new PublisherImpl[String]
  }

  "A Publisher" should {

    "start with an empty default value" in new Fixture {
      publisher.getValue shouldBe None
    }

    "start with an empty value for a specific target" in new Fixture {
      publisher.getValue("anyTarget") shouldBe None
    }

    "update its default value and notify a simple listener" in new Fixture {
      val mockListener = mock[Option[String] => Unit]
      publisher.addListener(mockListener)
      val newValue = Some("Hello, World!")

      verify(mockListener, times(1)).apply(None)

      publisher.update(newValue)

      publisher.getValue shouldBe newValue
      verify(mockListener, times(1)).apply(newValue)
    }

    "update a specific target's value" in new Fixture {
      val targetId = "ComponentX"
      val newValue = Some("Targeted Update")

      publisher.update(newValue, target = Some(targetId))

      publisher.getValue(targetId) shouldBe newValue
      publisher.getValue shouldBe None // Default value should be unaffected
    }

    "notify multiple listeners" in new Fixture {
      val listener1 = mock[Option[String] => Unit]
      val listener2 = mock[Option[String] => Unit]
      publisher.addListener(listener1)
      verify(listener1, times(1)).apply(None)

      publisher.addListener(listener2)
      verify(listener2, times(1)).apply(None)

      val newValue = Some("Update All")

      publisher.update(newValue)

      verify(listener1, times(1)).apply(newValue)
      verify(listener2, times(1)).apply(newValue)
    }

    "NOT notify a listener that comes from the same source as the update" in new Fixture {
      val sourceId = Some("ComponentA")
      val sameSourceListener = mock[Option[String] => Unit]

      publisher.addListener(sameSourceListener, source = sourceId)
      verify(sameSourceListener, times(1)).apply(None)

      val newValue = Some("This should be muted")

      publisher.update(newValue, source = sourceId)

      verify(sameSourceListener, never()).apply(newValue)
    }

    "notify a listener if the update comes from a DIFFERENT source" in new Fixture {
      val listenerSource = Some("ComponentA")
      val updateSource = Some("ComponentB")
      val listener = mock[Option[String] => Unit]
      publisher.addListener(listener, source = listenerSource)

      verify(listener, times(1)).apply(None)

      val newValue = Some("Crosstalk")

      publisher.update(newValue, source = updateSource)

      verify(listener, times(1)).apply(newValue)
    }

    "notify a listener subscribed to a specific target" in new Fixture {
      val targetId = "t1"
      val listener = mock[Option[String] => Unit]
      publisher.addListener(listener, target = Some(targetId))

      verify(listener, never()).apply(None)

      val newValue = Some("For t1 only")

      publisher.update(newValue, target = Some(targetId))

      verify(listener, times(1)).apply(newValue)
    }

    "NOT notify a listener for a different target" in new Fixture {
      val listenerForT1 = mock[Option[String] => Unit]
      publisher.addListener(listenerForT1, target = Some("t1"))

      verify(listenerForT1, never()).apply(None)

      val newValue = Some("Update for t2")

      publisher.update(newValue, target = Some("t2"))

      verify(listenerForT1, never()).apply(any())
    }

    "NOT notify a global listener of a targeted update" in new Fixture {
      val globalListener = mock[Option[String] => Unit]
      publisher.addListener(globalListener) // No target specified

      verify(globalListener, times(1)).apply(None)

      val newValue = Some("Update for t1")

      publisher.update(newValue, target = Some("t1"))

      verify(globalListener, times(1)).apply(None)
    }

    "NOT notify a targeted listener of a global update" in new Fixture {
      val targetedListener = mock[Option[String] => Unit]
      publisher.addListener(targetedListener, target = Some("t1"))
      val newValue = Some("Global update")

      publisher.update(newValue) // No target

      verify(targetedListener, never()).apply(any())
    }

    "remove a target and its associated value and listeners" in new Fixture {
      val targetId = "to-be-removed"
      val listener = mock[Option[String] => Unit]
      publisher.addListener(listener, target = Some(targetId))
      publisher.update(Some("Initial Value"), target = Some(targetId))

      // Verify initial state
      publisher.getValue(targetId) shouldBe Some("Initial Value")
      verify(listener, times(1)).apply(Some("Initial Value"))

      // Action
      publisher.removeTarget(targetId)

      // Assert value is gone
      publisher.getValue(targetId) shouldBe None

      // Assert listener is no longer notified
      publisher.update(Some("New Value"), target = Some(targetId))
      verify(listener, times(1)).apply(Some("Initial Value")) // No new invocations
    }
  }
}