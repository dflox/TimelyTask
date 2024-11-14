package me.timelytask.controller

import me.timelytask.model.Model
import me.timelytask.model.settings.{Action, Exit, SaveAndExit, StartApp, ViewType}
import me.timelytask.model.modelPublisher
import me.timelytask.controller.PersistenceController
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.*

class PersistenceControllerSpec extends AnyWordSpec with MockitoSugar {
  "The PersistenceController" should {
    "handle StartApp action correctly" in {
      val model = mock[Model]
      when(Model.default).thenReturn(model)

      val result = StartApp.call

      verify(modelPublisher).update(model)
      result shouldEqual true
    }
  }
}