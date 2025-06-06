package me.timelytask.experimental.notification

class EmailNotificationStrategy extends NotificationStrategy {
  override def sendNotification(notification: Notification): Unit = {
    println(s"Sending email to ${notification.recipient} with message: ${notification.message}")
    // -> Email api einfuegen
    // -> async senden um blocked zu vefrhindern wenn  wir emails senden
  }
}
