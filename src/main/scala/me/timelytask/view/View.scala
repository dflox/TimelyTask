package me.timelytask.view

import me.timelytask.view.viewmodel.ViewModel

trait View {
  def update(viewModel: ViewModel): String
}
