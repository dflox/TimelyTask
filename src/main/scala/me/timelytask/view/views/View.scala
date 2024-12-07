package me.timelytask.view.views

import me.timelytask.view.viewmodel.ViewModel

trait View[ViewType, ViewModelType <: ViewModel[ViewType]] {
  var viewModel: ViewModelType
  def update(viewModel: ViewModelType): Boolean
}
