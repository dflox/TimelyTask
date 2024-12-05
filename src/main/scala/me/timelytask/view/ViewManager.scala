package me.timelytask.view

import me.timelytask.view.viewmodel.ViewModel

trait ViewManager {
  def update(viewModel: ViewModel): Boolean
  
}
