package me.timelytask.notification



class SimpleNotification(override val message: String, override val recipient: String) extends CloneableNotification {
  
    override def deepClone(): CloneableNotification = {
    new SimpleNotification(message, recipient)
  }
}