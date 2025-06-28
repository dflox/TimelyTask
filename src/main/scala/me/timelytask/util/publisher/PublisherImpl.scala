package me.timelytask.util.publisher

import me.timelytask.util.Publisher

import scala.collection.mutable
import scala.collection.mutable.Map

class PublisherImpl[T] extends Publisher[T] {
  private val value: mutable.Map[Any, Option[T]] = mutable.Map.empty
  private var defaultValue: Option[T] = None
  private var listeners: List[(Option[T] => Unit, Option[Any], Option[Any])] = List()

  override def addListener(listener: Option[T] => Unit, source: Option[Any] = None, 
                           target: Option[Any] = None)
  : Unit = {
    listeners = (listener, source, target) :: listeners
    if (target.isDefined) listener(value.get(target.get).flatten)
  }

  override def update(newValue: Option[T], source: Option[Any] = None, target: Option[Any] = None)
  : Unit = {
    if newValue.isDefined && target.isDefined then value(target.get) = newValue
    else if newValue.isDefined then defaultValue = newValue
    listeners.foreach {
      case (listener, listenerSource, listenerTarget) =>
        if listenerSource != source || listenerSource.isEmpty then
          if target.isEmpty || listenerTarget.contains(target.get) then listener(newValue)
    }
  }

  override def getValue(target: Any): Option[T] = value.get(target).flatten
  
  override def getValue: Option[T] = defaultValue

  override def removeTarget(target: Any): Unit = { 
    value.remove(target)
    listeners = listeners.filterNot(_._3.contains(target))
  }
}