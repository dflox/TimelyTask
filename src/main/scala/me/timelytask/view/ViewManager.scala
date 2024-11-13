package me.timelytask.view

import me.timelytask.model.settings.ViewType
import me.timelytask.model.settings.ViewType.CALENDAR
import me.timelytask.util.{Observer, Publisher}
import me.timelytask.view.tui.{CalendarTUI, TUIView}
import me.timelytask.view.viewmodel.{TUIModel, ViewModel}

class ViewManager(viewModelPublisher: Publisher[ViewModel]) extends Observer[ViewType] {
  private var activeView: ViewType = ViewType.CALENDAR
  private var lastTuiModel: TUIModel = TUIModel.default

  def renderActiveTUIView(tuiModel: TUIModel): String = {
    lastTuiModel = tuiModel
    renderActiveTUIView()
  }

  def renderActiveTUIView(): String = {
    val view: TUIView = activeView
    val model = viewModelPublisher.getValue
    view.update(model, lastTuiModel)
  }

  override def onChange(viewType: ViewType): Unit = {
    activeView = viewType
  }
}

given Conversion[ViewType, TUIView] with {
  def apply(viewType: ViewType): TUIView = viewType match {
    case CALENDAR => CalendarTUI
    case _ => CalendarTUI
    //    case TABLE => ???
    //    case KANBAN => ???
    //    case SETTINGS => ???
    //    case TASK => ???
  }
}
