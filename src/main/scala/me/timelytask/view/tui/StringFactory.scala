package me.timelytask.view.tui

import me.timelytask.model.settings.ViewType
import me.timelytask.view.viewmodel.ViewModel

trait StringFactory[VT <: ViewType, ViewModelType <: ViewModel[VT]] {
  def buildString(viewModel: ViewModelType): String

  def buildString(viewModel: ViewModelType, TUIModel: ModelTUI): String
}