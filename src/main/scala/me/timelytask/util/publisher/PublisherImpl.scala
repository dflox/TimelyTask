package me.timelytask.util.publisher

import me.timelytask.util.Publisher

import scala.collection.mutable
import scala.collection.mutable.Map

class PublisherImpl[T] extends Publisher[T] {
  private case class ValueWithSource(value: Option[T], source: Option[Any])
  private case class Listener(listener: Option[T] => Unit, source: Option[Any], target: Option[Any])
  private val value: mutable.Map[Any, ValueWithSource] = mutable.Map.empty
  private var defaultValue: ValueWithSource = ValueWithSource(None, None)
  private var listeners: List[Listener] = List()

  override def addListener(listener: Option[T] => Unit, source: Option[Any] = None, 
                           target: Option[Any] = None)
  : Unit = {
    val newListener = Listener(listener, source, target)
    listeners = newListener :: listeners
    if target.isDefined then {
      callTargetedListener(newListener)
    } else {
      callDefaultListener(newListener)
    }
  }

  override def update(newValue: Option[T], source: Option[Any] = None, target: Option[Any] = None)
  : Unit = {
    if target.isDefined then {
      value(target.get) = ValueWithSource(newValue, source)
      if newValue.isDefined then listeners.foreach(callTargetedListener)
    } else {
      defaultValue = ValueWithSource(newValue, source)
      if defaultValue.value.isDefined then listeners.foreach(callDefaultListener)
    }
  }

  override def getValue(target: Any): Option[T] = value.get(target).flatMap(_.value)
  
  override def getValue: Option[T] = defaultValue.value

  override def removeTarget(target: Any): Unit = { 
    value.remove(target)
    listeners = listeners.filterNot(_.target.contains(target))
  }
  
  private def callTargetedListener(listener: Listener): Unit
  = {
    if listener.target.isDefined then {
      val callValue = value.get(listener.target.get)
      if callValue.isDefined then {
        if callValue.get.source.isEmpty || !callValue.get.source.contains(listener.source) 
        then listener.listener(callValue.flatMap(_.value))
      }
    }
  }
  
  private def callDefaultListener(listener: Listener): Unit = {
    if listener.target.isEmpty && (listener.source.isEmpty 
      || !listener.source.exists(defaultValue.source.contains)) then {
      listener.listener(defaultValue.value)
    }
  }
}