package me.timelytask.notification

trait NotificationStrategy {
  def sendNotification(notification: Notification): Unit
}
