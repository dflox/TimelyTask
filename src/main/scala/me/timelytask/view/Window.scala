package me.timelytask.view

import me.timelytask.model.utility.Keyboard
import me.timelytask.util.Observer
import me.timelytask.view.viewmodel.{TUIModel, ViewModel}
import org.jline.terminal.Terminal

import java.io.PrintWriter


class Window(terminal: Terminal, inputHandler: InputHandler, viewManager: ViewManager)
  extends Observer[ViewModel] {
  val writer: PrintWriter = terminal.writer()

  def onUserInput(key: Keyboard): Unit = {
    inputHandler.handleInput(key)
  }

  def onChange(newViewModel: ViewModel): Unit = {
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
