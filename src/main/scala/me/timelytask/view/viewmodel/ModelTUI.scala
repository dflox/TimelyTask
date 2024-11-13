package me.timelytask.view.viewmodel

case class ModelTUI(terminalHeight: Int,
                    terminalWidth: Int
                   ) {
}

object ModelTUI {
  val default: ModelTUI = ModelTUI(24, 80)
}
