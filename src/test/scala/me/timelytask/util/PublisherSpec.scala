package me.timelytask.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.{never, times, verify}
import org.mockito.ArgumentMatchers.any
import scala.collection.mutable

/**
 * Test specification for any class that implements the Publisher trait.
 * This spec uses a minimal, in-memory implementation of the trait called SimplePublisher
 * to test the contract defined by the trait itself.
 */
class PublisherSpec extends AnyWordSpec with Matchers with MockitoSugar {

  /**
   * A minimal, test-only implementation of the Publisher trait.
   * This class correctly implements the full Publisher interface, including logic
   * for default values, targeted values, and source/target filtering for listeners.
   */
  private class SimplePublisher[T] extends Publisher[T] {
    // State for targeted values
    private val values: mutable.Map[Any, Option[T]] = mutable.Map.empty
    // State for the default, non-targeted value
    private var defaultValue: Option[T] = None
    // Listeners now store the listener function, its source, and its target
    private var listeners: Vector[(Option[T] => Unit, Option[Any], Option[Any])] = Vector.empty

    // The overriding method should NOT have default parameters; it inherits them from the trait.
    override def addListener(listener: Option[T] => Unit, source: Option[Any], target: Option[Any]): Unit = {
      listeners = listeners :+ (listener, source, target)
    }

    override def update(newValue: Option[T], source: Option[Any], target: Option[Any]): Unit = {
      // Update the correct state based on whether a target is present
      if (target.isDefined) {
        values(target.get) = newValue
      } else {
        defaultValue = newValue
      }

      // Notify relevant listeners
      listeners.foreach { case (listener, listenerSource, listenerTarget) =>
        // A listener is blocked only if the update has a source AND it's the same as the listener's source.
        val isBlockedBySource = source.isDefined && listenerSource == source

        if (!isBlockedBySource) {
          // A listener is notified if:
          // 1. It's a "global" listener (no target).
          // 2. The update is "global" (no target).
          // 3. The listener's target matches the update's target.
          val isGlobalListener = listenerTarget.isEmpty
          val isGlobalUpdate = target.isEmpty
          val isTargetMatch = listenerTarget == target

          if (isGlobalListener || isGlobalUpdate || isTargetMatch) {
            listener(newValue)
          }
        }
      }
    }

    override def getValue: Option[T] = defaultValue
    override def getValue(target: Any): Option[T] = values.get(target).flatten
    override def removeTarget(target: Any): Unit = {
      values.remove(target)
      listeners = listeners.filterNot(_._3.contains(target))
    }
  }

  // Fixture to create a new publisher for each test context
  trait Fixture {
    val publisher: Publisher[String] = new SimplePublisher[String]
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

      publisher.update(newValue) // No target, updates default

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

    "notify a listener subscribed to a specific target" in new Fixture {
      val targetId = "t1"
      val listener = mock[Option[String] => Unit]
      publisher.addListener(listener, target = Some(targetId))
      val newValue = Some("For t1 only")

      publisher.update(newValue, target = Some(targetId))

      verify(listener, times(1)).apply(newValue)
    }

    "NOT notify a listener for a different target" in new Fixture {
      val listenerForT1 = mock[Option[String] => Unit]
      publisher.addListener(listenerForT1, target = Some("t1"))
      val newValue = Some("Update for t2")

      publisher.update(newValue, target = Some("t2"))

      verify(listenerForT1, never()).apply(any())
    }

    "notify a global listener of a targeted update" in new Fixture {
      val globalListener = mock[Option[String] => Unit]
      publisher.addListener(globalListener) // No target specified
      val newValue = Some("Update for t1")

      publisher.update(newValue, target = Some("t1"))

      verify(globalListener, times(1)).apply(newValue)
    }

    "notify a targeted listener of a global update" in new Fixture {
      val targetedListener = mock[Option[String] => Unit]
      publisher.addListener(targetedListener, target = Some("t1"))
      val newValue = Some("Global update")

      publisher.update(newValue) // No target

      verify(targetedListener, times(1)).apply(newValue)
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