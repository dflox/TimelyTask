package view.tui

import view.View
import view.viewmodel.*
import view.viewmodel.TUIModel

trait TUIView extends View {
  def update(viewModel: ViewModel): String
  def update(viewModel: ViewModel, TUIModel: TUIModel): String
}