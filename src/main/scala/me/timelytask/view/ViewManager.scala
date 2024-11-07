package me.timelytask.view

import me.timelytask.controller.ViewModelPublisher
import me.timelytask.model.settings.ViewType
import me.timelytask.view.viewmodel.TUIModel

class ViewManager (viewModelPublisher: ViewModelPublisher) {
  private var activeView: ViewType = ViewType.CALENDAR

  def renderActiveTUIView(tuiModel: TUIModel): String = {
    val view = activeView.getTUIView
    val model = viewModelPublisher.getCurrentViewModel
    view.update(model, tuiModel)
  }
}
