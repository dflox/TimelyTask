package me.timelytask.model.utility

trait DeepCloneable {
  def deepClone(): DeepCloneable
}
// hier eigentlich für Prototype Pattern, ist aber irgendwo auch ein Decorator Pattern