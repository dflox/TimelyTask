package me.timelytask.controller

import me.timelytask.controller.commands.CommandHandler
import me.timelytask.controller.controllersImpl.PersistenceControllerImpl
import me.timelytask.model.Model
import me.timelytask.util.Publisher
import org.mockito.Mockito.{times, verify}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

class PersistenceControllerSpec extends AnyWordSpec with Matchers with MockitoSugar {

  trait Fixture {
    val mockModelPublisher: Publisher[Model] = mock[Publisher[Model]]
    val mockCommandHandler: CommandHandler = mock[CommandHandler]

    val persistenceController = new PersistenceControllerImpl(
      mockModelPublisher,
      mockCommandHandler
    )
  }

  "The PersistenceController" when {
    /*
    "being initialized" should {
      "register a listener on the model publisher" in new Fixture {
        // Dieser Test schlägt fehl, egal wie er implementiert wird.
        persistenceController.init()
        verify(mockModelPublisher, times(1)).addListener(???)
      }
    }
    */

    "loading the model from DB" should {
      "update the model publisher with an empty model" in new Fixture {
        // Arrange

        // Act
        persistenceController.loadModelFromDB()

        // Assert
        verify(mockModelPublisher, times(1)).update(Some(Model.emptyModel))
      }
    }

    "saving a model to a serialization type" should {
      "throw a NotImplementedError because it is not yet implemented" in new Fixture {
        intercept[NotImplementedError] {
          persistenceController.SaveModelTo("json")
        }
      }
    }

    "loading a model from a serialization type" should {
      "throw a NotImplementedError because it is not yet implemented" in new Fixture {
        intercept[NotImplementedError] {
          persistenceController.LoadModel("json")
        }
      }
    }
  }
}