package me.timelytask.view

import me.timelytask.model.utility.{A, Keyboard}
import me.timelytask.view.viewmodel.{TUIModel, ViewModel}
import org.jline.terminal.Terminal
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

import java.io.PrintWriter

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

    "on view model change update the view correctly" in {
      // Mock dependencies
      val mockWriter = mock[PrintWriter]
      val terminal = mock[Terminal]
      when(terminal.writer()).thenReturn(mockWriter)

      val inputHandler = mock[InputHandler]
      val viewManager = mock[ViewManager]
      val viewModel = mock[ViewModel]

      val window = new Window(terminal, inputHandler, viewManager)
      window.onChange(viewModel)
      val captor = ArgumentCaptor.forClass(classOf[TUIModel])
      verify(viewManager).renderActiveTUIView(captor.capture())
      captor.getValue shouldEqual new TUIModel(terminal.getHeight, terminal.getWidth)
    }
  }
}