package view

import controller.ViewModelPublisher
import model.settings.ViewType
import view.viewmodel.ViewModel

class ViewManager (viewModelPublisher: ViewModelPublisher) {
  private var activeView: ViewType = ViewType.CALENDAR

  def renderActiveTUIView(tuiModel: TUIModel): String = {
    val view = activeView.getTUIView
    val model = viewModelPublisher.getCurrentViewModel
    view.update(model, tuiModel)
  }

  def getActiveViewModel: ViewModel = {
    viewModelPublisher.getCurrentViewModel
  }
}
