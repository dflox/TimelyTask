package me.timelytask.view.tui

import me.timelytask.model.settings.ViewType
import me.timelytask.util.Publisher
import org.jline.terminal.Terminal

import java.io.PrintWriter

trait TuiWindow {
  val terminal: Terminal;
  val activeViewPublisher: Publisher[ViewType]
  def run(): Unit
  val writer: PrintWriter = terminal.writer()

}
