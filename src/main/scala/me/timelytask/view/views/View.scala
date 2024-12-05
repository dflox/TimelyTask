package me.timelytask.view.views

import me.timelytask.view.viewmodel.ViewModel

trait View {
  def update(viewModel: ViewModel): Boolean
}
