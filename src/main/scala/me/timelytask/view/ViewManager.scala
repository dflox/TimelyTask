package me.timelytask.view

import me.timelytask.controller.{ActiveViewObserver, ActiveViewPublisher, ViewModelPublisher}
import me.timelytask.model.settings.ViewType
import me.timelytask.view.viewmodel.TUIModel

class ViewManager (viewModelPublisher: ViewModelPublisher) extends ActiveViewObserver{
  private var activeView: ViewType = ViewType.CALENDAR
  private var lastTuiModel: TUIModel = TUIModel.default

  def renderActiveTUIView(tuiModel: TUIModel): String = {
    lastTuiModel = tuiModel
    renderActiveTUIView()
  }
  
  def renderActiveTUIView(): String = {
    val view = activeView.getTUIView
    val model = viewModelPublisher.getCurrentViewModel
    view.update(model, lastTuiModel)
  }

  override def onActiveViewChange(viewType: ViewType): Unit = {
    activeView = viewType
  }
}
