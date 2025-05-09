package me.timelytask.util.publisher

import me.timelytask.util.Publisher

class PublisherImpl[T](private var value: Option[T]) extends Publisher[T] {
  private var listeners: List[(Option[T] => Unit, Option[Any])] = List()

  override def addListener(listener: Option[T] => Unit, source: Option[Any] = None): Unit = {
    listeners = (listener, source) :: listeners
  }

  override def update(newValue: Option[T], source: Option[Any] = None): Unit = {
    if newValue.isDefined then value = newValue
    listeners.foreach { case (listener, listenerSource) =>
      if (listenerSource != source || listenerSource.isEmpty || source.isEmpty) listener(value)
    }
  }

  override def getValue: Option[T] = value
}