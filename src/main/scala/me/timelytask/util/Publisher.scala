package me.timelytask.util

import scala.Option

/**
 * Trait for a generic publisher that can notify listeners of changes.
 * @tparam T the type of value being published
 */
trait Publisher[T] {

  /** 
   * Adds a listener function to the publisher with an optional source identifier.
   * The listener will be called whenever the value changes.
   * If the source identifier is provided, the publisher will ignore updates from the same source.
   * @param listener function to be called when the value changes
   * @param source optional source identifier
   */
  def addListener(listener: Option[T] => Unit, source: Option[Any] = 
  None, target: Option[Any] = None): Unit

  /**
   * Update the value and notify all listeners, with an optional source identifier.
   * Source identifier is used to ignore listeners from the same source.
   * @param newValue value to be updated
   * @param source source identifier
   */
  def update(newValue: Option[T], source: Option[Any] = None, target: Option[Any] = None): Unit

  /** 
   * Retrieve the current value
   * @return
   */
  def getValue: Option[T]

  /**
   * Retrieve the value for a specific target.
   *
   * @return
   */
  def getValue(target: Any): Option[T]
  
  /**
   * Remove a listener for a specific target and all values published for this target.
   * @param target the target for which the listener should be removed
   */
  def removeTarget(target: Any): Unit
}
