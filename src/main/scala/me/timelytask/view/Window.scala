package me.timelytask.view

import me.timelytask.controller.ViewModelObserver
import me.timelytask.model.utility.Keyboard
import me.timelytask.view.viewmodel.{TUIModel, ViewModel}
import org.jline.terminal.Terminal

import java.io.PrintWriter


class Window (terminal: Terminal, inputHandler: InputHandler, viewManager: ViewManager) extends ViewModelObserver {
  val writer: PrintWriter = terminal.writer()
  
  def onUserInput(key: Keyboard): Unit = {
    // Call InputHandler to interpret the event and delegate appropriately
    //writer.println("key pressed: " + key)
    inputHandler.handleInput(key)
  }

  def onViewModelChange(newViewModel: ViewModel): Unit = {
    // Whenever the ViewModel is updated, re-render the current view
    //writer.println("ViewModel updated")
    updateView()
  }
  
  def updateView(): Unit = {
    val tuiModel: TUIModel = new TUIModel(terminal.getHeight, terminal.getWidth)
    // Render the current view
    writer.println(viewManager.renderActiveTUIView(tuiModel))
  }
}
