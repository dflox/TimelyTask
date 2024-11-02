package view

import controller.ViewModelPublisher
import model.settings.ViewType
import view.CalendarTUI

class ViewManager (viewModelPublisher: ViewModelPublisher) {
  private var activeView: ViewType = ViewType.CALENDAR

  def renderActiveTUIView(tuiModel: TUIModel): String = {
    val view = activeView.getTUIView
    val model = viewModelPublisher.getCurrentViewModel
    view.update(model, tuiModel)
  }  
}
