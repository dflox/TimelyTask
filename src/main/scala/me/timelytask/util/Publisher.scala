package me.timelytask.util

trait MultiTypeObserver {
  // Method to observe a publisher with a specific handler function
  def observe[T](publisher: Publisher[T])(handler: T => Unit, source: Option[Any]): Unit = {
    publisher.addListener(handler, source)
  }
}

class Publisher[T](private var value: T) {
  private var listeners: List[(T => Unit, Option[Any])] = List()

  // Method to add a listener function with an optional source identifier
  def addListener(listener: T => Unit, source: Option[Any] = None): Unit = {
    listeners = (listener, source) :: listeners
  }

  // Method to update value and notify all listeners, with an optional source identifier
  def update(newValue: T, source: Option[Any] = None): Unit = {
    value = newValue
    listeners.foreach { case (listener, listenerSource) =>
      // Execute listener only if it has a different source or no source
      if (listenerSource != source) listener(value)
    }
  }

  // Retrieve the current value
  def getValue: T = value
}

