package me.timelytask.experimental.notification

trait NotificationStrategy {
  def sendNotification(notification: Notification): Unit
}
