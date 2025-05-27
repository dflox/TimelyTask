package me.timelytask.notification

trait CloneableNotification extends Notification
                            with Cloneable {
  def deepClone(): CloneableNotification
}