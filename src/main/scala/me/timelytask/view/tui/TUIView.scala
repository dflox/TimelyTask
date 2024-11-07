package me.timelytask.view.tui

import me.timelytask.view.View
import me.timelytask.view.viewmodel.{TUIModel, ViewModel}

trait TUIView extends View {
  def update(viewModel: ViewModel): String
  def update(viewModel: ViewModel, TUIModel: TUIModel): String
}