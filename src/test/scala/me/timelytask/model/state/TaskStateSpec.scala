package me.timelytask.model.state

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import scalafx.scene.paint.Color

import java.util.UUID

class TaskStateSpec extends AnyWordSpec with Matchers with TableDrivenPropertyChecks {

  trait Fixture {
    val commonUuid: UUID = UUID.randomUUID()
    val openState = new OpenState("Open", "desc", null, commonUuid)
    val closedState = new ClosedState("Closed", "desc", null, commonUuid)
    val deletedState = new DeletedState("Deleted", "desc", null, UUID.randomUUID())
  }

  "The TaskState trait" should {

    "define equality based on UUID" in new Fixture {
      openState.equals(closedState) shouldBe true
      openState shouldEqual closedState
      openState.equals(deletedState) shouldBe false
      openState shouldNot equal(deletedState)
    }

    "return false when equals is called with a non-TaskState object or null" in new Fixture {
      openState.equals("a string") shouldBe false
      openState.equals(null) shouldBe false
    }

    "correctly derive the stateType string from the class name" in new Fixture {
      val stateTypeTable =
        Table(
          ("State Instance", "Expected Type String"),
          (new OpenState("o", "d", null), "open"),
          (new ClosedState("c", "d", null), "closed"),
          (new DeletedState("d", "d", null), "deleted")
        )

      forAll(stateTypeTable) { (stateInstance, expectedTypeString) =>
        stateInstance.stateType shouldBe expectedTypeString
      }
    }
  }

  "The TaskState companion object" should {

    "the simple apply factory method" should {
      "always create an instance of OpenState" in {
        val state = TaskState("New State", "A description", null)
        state shouldBe an[OpenState]
        state.name shouldBe "New State"
      }
    }

    "the complex apply factory method" should {
      val testUuid = UUID.randomUUID()

      val factoryTable =
        Table(
          ("State Type String", "Expected Class"),
          ("open", classOf[OpenState]),
          ("closed", classOf[ClosedState]),
          ("deleted", classOf[DeletedState])
        )

      forAll(factoryTable) { (stateTypeString, expectedClass) =>
        s"create an instance of ${expectedClass.getSimpleName} for stateType '$stateTypeString'" in {
          val state = TaskState("Factory Test", "desc", null, stateTypeString, testUuid)
          expectedClass.isInstance(state) shouldBe true

          state.uuid shouldBe testUuid
        }
      }

      "throw an IllegalArgumentException for an unknown state type" in {
        val unknownType = "invalid_state_type"
        val exception = a[IllegalArgumentException] should be thrownBy {
          TaskState("Error Test", "desc", null, unknownType, testUuid)
        }
      }
    }
  }
}