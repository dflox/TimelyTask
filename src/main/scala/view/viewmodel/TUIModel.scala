package view.viewmodel

case class TUIModel(terminalHeight: Int,
                    terminalWidth: Int
                   ) {
}

object TUIModel {
  val default: TUIModel = TUIModel(24, 80)
}
