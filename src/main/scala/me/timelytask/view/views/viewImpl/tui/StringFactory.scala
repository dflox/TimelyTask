package me.timelytask.view.views.viewImpl.tui

import me.timelytask.model.settings.ViewType
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.views.viewImpl.tui.ModelTUI

trait StringFactory[VT <: ViewType, ViewModelType <: ViewModel[VT, ViewModelType]] {
  def buildString(viewModel: ViewModelType): String

  def buildString(viewModel: ViewModelType, TUIModel: ModelTUI): String
}