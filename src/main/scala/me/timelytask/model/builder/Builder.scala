package me.timelytask.model.builder

trait Builder[T](defaultInstance: T) {
  def build(): T
}