package view

trait View {
  def update(viewModel: ViewModel): String
}

trait TUIView extends View {
  def update(viewModel: ViewModel): String
  def update(viewModel: ViewModel, TUIModel: TUIModel): String
}
