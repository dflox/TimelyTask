package me.timelytask.experimental.notification

trait CloneableNotification extends Notification
                            with Cloneable {
  def deepClone(): CloneableNotification
}