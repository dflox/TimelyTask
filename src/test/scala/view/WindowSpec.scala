package view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.mockito.ArgumentCaptor
import org.jline.terminal.Terminal
import model.utility.*

class WindowSpec extends AnyWordSpec with MockitoSugar {

  "The Window" should {

    "handle user input correctly" in {
      // Mock dependencies
      val terminal = mock[Terminal]
      val inputHandler = mock[InputHandler]
      val viewManager = mock[ViewManager]
      
      val window = new Window(terminal, inputHandler, viewManager)
      val key = A
      window.onUserInput(key)
      
      val captor = ArgumentCaptor.forClass(classOf[Keyboard])
      verify(inputHandler).handleInput(captor.capture())
      captor.getValue shouldEqual key
    }
  }
}