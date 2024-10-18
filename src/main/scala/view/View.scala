package view

trait View[T] {
  def render(model: T): String
}
