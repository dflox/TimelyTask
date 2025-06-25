package me.timelytask.view.views.viewImpl.tui

import me.timelytask.util.CancelableFuture
import me.timelytask.view.views.MinimalStartUpView
import org.jline.reader.LineReaderBuilder
import org.jline.terminal.{Terminal, TerminalBuilder}
import org.jline.utils.InfoCmp.Capability

import scala.util.Try

class TUIMinimalStartupView extends MinimalStartUpView{
  val terminal: Terminal = TerminalBuilder.builder()
    .dumb(false)
    .build()
  
  override def render(onUserInput: String => Unit): Unit = {
    terminal.puts(Capability.clear_screen)
    terminal.flush()
    
    val writer = terminal.writer()
    writer.println("╔════════════════════════════════════╗")
    writer.println("║   Willkommen bei TimelyTask TUI    ║")
    writer.println("╚════════════════════════════════════╝")
    writer.println()
    writer.println("Bitte geben Sie Ihren Benutzernamen ein:")
    writer.flush()

    val lineReader = LineReaderBuilder.builder()
      .terminal(terminal)
      .build()
    
    keyInputTask = Some(CancelableFuture {
      var username: String = ""

      while (username.isEmpty) {
        username = Try(lineReader.readLine("> "))
          .toOption
          .map(_.trim)
          .filter(_.nonEmpty)
          .getOrElse {
            writer.println("Benutzername darf nicht leer sein. Bitte erneut eingeben.")
            writer.flush()
            ""
          }
      }
      onUserInput(username)
    })
  }
  
  override def kill(): Unit = {
    stopInput()
    terminal.puts(Capability.clear_screen)
    terminal.flush()
    terminal.close()
  }

  private var keyInputTask: Option[CancelableFuture[Unit]] = None

  private def stopInput(): Unit = {
    keyInputTask.foreach(_.cancel())
  }
}
