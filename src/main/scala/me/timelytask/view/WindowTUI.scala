package me.timelytask.view

import me.timelytask.model.settings.ViewType
import me.timelytask.model.settings.ViewType.CALENDAR
import me.timelytask.model.utility.Keyboard
import me.timelytask.util.Publisher
import me.timelytask.view.tui.{CalendarTUI, TUIView}
import me.timelytask.view.viewmodel.{ModelTUI, ViewModel}
import org.jline.terminal.Terminal

import java.io.PrintWriter

class WindowTUI(using viewModelPublisher: Publisher[ViewModel], terminal: Terminal,
                inputHandler: InputHandler, activeViewPublisher: Publisher[ViewType]) {
  private val activeView: () => ViewType = () => activeViewPublisher.getValue

  val writer: PrintWriter = terminal.writer()

  def onUserInput(key: Keyboard): Unit = {
    inputHandler.handleInput(key)
  }

  viewModelPublisher.addListener { viewModel =>
    renderActiveView(viewModel)
  }

  def renderActiveView(viewModel: ViewModel): Unit = {
    val TUIView = activeView()
    writer.print(TUIView.update(viewModel, new ModelTUI(terminal.getHeight, terminal.getWidth)))
  }
}

given Conversion[ViewType, TUIView] with {
  def apply(viewType: ViewType): TUIView = viewType match {
    case CALENDAR => CalendarTUI
    //case _ => CalendarTUI
    //    case TABLE => ???
    //    case KANBAN => ???
    //    case SETTINGS => ???
    //    case TASK => ???
  }
}
