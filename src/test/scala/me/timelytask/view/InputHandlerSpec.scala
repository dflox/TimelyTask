package me.timelytask.view

import me.timelytask.controller.*
import me.timelytask.model.settings.*
import me.timelytask.model.utility.*
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import me.timelytask.controller.keyMapManager

class InputHandlerSpec extends AnyWordSpec with MockitoSugar {
  "The InputHandler" should {
    "handle input and call the correct action" in {
      val inputHandler = new InputHandler

      val key = mock[Key]
      val action = mock[Action]

      when(keyMapManager.getActiveActionKeymap).thenReturn(Map(key -> action))
      when(keyMapManager.getGlobalActionKeymap).thenReturn(Map())
      when(action.call).thenReturn(true)

      val result = inputHandler.handleInput(key)

      result shouldEqual true
      verify(action).call
    }

    "handle input and return NoAction if no action is found" in {
      val inputHandler = new InputHandler

      val key = mock[Key]

      when(keyMapManager.getActiveActionKeymap).thenReturn(Map())
      when(keyMapManager.getGlobalActionKeymap).thenReturn(Map())

      val result = inputHandler.handleInput(key)

      result shouldEqual false
    }
  }
}