package me.timelytask.view.tui

import me.timelytask.view.view.View
import me.timelytask.view.viewmodel.{ModelTUI, ViewModel}

trait StringFactory extends View {
  def update(viewModel: ViewModel): String

  def update(viewModel: ViewModel, TUIModel: ModelTUI): String
}