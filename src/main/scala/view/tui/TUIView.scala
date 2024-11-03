package view.tui

import view.viewmodel.ViewModel
import view.{TUIModel, View}

trait TUIView extends View {
  def update(viewModel: ViewModel): String
  def update(viewModel: ViewModel, TUIModel: TUIModel): String
}