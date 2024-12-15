package me.timelytask.view.viewmodel.viewchanger

import me.timelytask.model.settings.ViewType
import me.timelytask.view.viewmodel.ViewModel

trait ViewChangeArgument[VT <: ViewType, VM <: ViewModel[VT]] {
  def apply(viewModel: Option[VM]): Option[VM]
}
