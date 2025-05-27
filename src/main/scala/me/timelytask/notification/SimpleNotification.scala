package me.timelytask.notification


// Subclass Prototype (siehe https://refactoring.guru/design-patterns/prototype)
class SimpleNotification(override val message: String, override val recipient: String)
  extends CloneableNotification {

  override def deepClone(): CloneableNotification = {
    new SimpleNotification(message, recipient)
  }
}