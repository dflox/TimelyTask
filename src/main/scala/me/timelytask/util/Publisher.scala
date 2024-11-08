package me.timelytask.util

trait Observer[T] {
  def onChange(value: T): Unit
}

class Publisher[T](private var value: T) {
  private var observers: List[Observer[T]] = List()

  def subscribe(observer: Observer[T]): Unit = {
    observers = observer :: observers
  }

  def update(value: T): Unit = {
    this.value = value
    observers.foreach(_.onChange(value))
  }

  def getValue: T = value
}
