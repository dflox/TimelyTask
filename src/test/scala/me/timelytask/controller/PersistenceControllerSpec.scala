package me.timelytask.controller

import me.timelytask.controller.commands.CommandHandler
import me.timelytask.controller.controllersImpl.PersistenceControllerImpl
import me.timelytask.model.Model
import me.timelytask.serviceLayer.ServiceModule
import me.timelytask.util.Publisher
import org.mockito.Mockito.{times, verify}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

class PersistenceControllerSpec extends AnyWordSpec with Matchers with MockitoSugar {

  trait Fixture {
    val mockModelPublisher: Publisher[Model] = mock[Publisher[Model]]
    val mockCommandHandler: CommandHandler = mock[CommandHandler]
    val mockServiceModule: ServiceModule = mock[ServiceModule]

    val persistenceController = new PersistenceControllerImpl(
      mockModelPublisher,
      mockServiceModule,
      mockCommandHandler
    )
  }

  "The PersistenceController" when {
    "saving a model to a serialization type" should {
      
    }

    "loading a model from a serialization type" should {
      
    }
  }
}