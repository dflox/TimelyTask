package view

import view.model.ViewModel

trait View[+T <: ViewModel] {
  def update(model: ViewModel): String
}
