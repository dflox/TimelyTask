package me.timelytask.view.views

import me.timelytask.core.CoreModule
import me.timelytask.model.settings.StartUp

trait MinimalStartUpView {
  def render(onUserInput: String => Unit): Unit
  def kill(): Unit
}
