package me.timelytask.view.tui

import me.timelytask.view.viewmodel.ViewModel

trait StringFactory {
  def buildString(viewModel: ViewModel): String

  def buildString(viewModel: ViewModel, TUIModel: ModelTUI): String
}