package me.timelytask.view.views.viewImpl.tui

case class ModelTUI(terminalHeight: Int,
                    terminalWidth: Int) {
}

object ModelTUI {
  val default: ModelTUI = ModelTUI(24, 80)
}
