package view

trait View[T] {
  def update(model: T): String
}
