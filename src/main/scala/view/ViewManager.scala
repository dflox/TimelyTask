package view

import controller.ViewModelPublisher
import model.settings.ViewType

class ViewManager (viewModelPublisher: ViewModelPublisher) {
  private var activeView: ViewType = ViewType.CALENDAR

  def renderActiveTUIView(tuiModel: TUIModel): String = {
    val view = activeView.getTUIView
    val model = viewModelPublisher.getCurrentViewModel
    view.update(model, tuiModel)
  }
}
