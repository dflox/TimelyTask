package view

import view.model.ViewModel

trait View {
  def update(model: ViewModel): String
}
