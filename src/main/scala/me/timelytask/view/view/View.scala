package me.timelytask.view.view

import me.timelytask.view.viewmodel.ViewModel

trait View {
  def update(viewModel: ViewModel): Boolean
}
