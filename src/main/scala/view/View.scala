package view

import view.viewmodel.ViewModel

trait View {
  def update(viewModel: ViewModel): String
}
