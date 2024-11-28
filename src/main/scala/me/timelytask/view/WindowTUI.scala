package me.timelytask.view

import me.timelytask.model.settings.ViewType
import me.timelytask.model.settings.ViewType.{CALENDAR, TASK}
import me.timelytask.model.utility.Key
import me.timelytask.util.Publisher
import me.timelytask.view.tui.{CalendarTUI, TUIView, TaskTUI}
import me.timelytask.view.viewmodel.{ModelTUI, ViewModel}
import org.jline.terminal.Terminal

import java.io.PrintWriter

class WindowTUI(using viewModelPublisher: Publisher[ViewModel], terminal: Terminal,
                inputHandler: InputHandler, activeViewPublisher: Publisher[ViewType]) {
  private val activeView: () => ViewType = () => activeViewPublisher.getValue

  println("WindowTUI")
  
  val writer: PrintWriter = terminal.writer()

  def onUserInput(key: Key): Unit = {
    inputHandler.handleInput(key)
  }

  viewModelPublisher.addListener { viewModel =>
    renderActiveView(viewModel)
  }

  def renderActiveView(viewModel: ViewModel): Unit = {
    val tuiView: TUIView = activeView()
    writer.print(tuiView.update(viewModel, new ModelTUI(terminal.getHeight, terminal.getWidth)))
  }
}

given Conversion[ViewType, TUIView] with {
  def apply(viewType: ViewType): TUIView = viewType match {
    case CALENDAR => CalendarTUI
    case TASK => TaskTUI
    //case _ => CalendarTUI
    //    case TABLE => ???
    //    case KANBAN => ???
    //    case SETTINGS => ???
    //    case TASK => ???
  }
}
