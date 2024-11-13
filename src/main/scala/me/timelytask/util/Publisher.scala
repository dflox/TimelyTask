package me.timelytask.util

trait MultiTypeObserver {
  // Method to observe a publisher with a specific handler function
  def observe[T](publisher: Publisher[T])(handler: T => Unit): Unit = {
    publisher.addListener(handler)
  }
}

class Publisher[T](private var value: T) {
  private var listeners: List[T => Unit] = List()

  // Method to add a listener function
  def addListener(listener: T => Unit): Unit = {
    listeners = listener :: listeners
  }

  // Method to update value and notify all listeners
  def update(newValue: T): Unit = {
    value = newValue
    listeners.foreach(listener => listener(value))
  }

  // Retrieve the current value
  def getValue: T = value
}
