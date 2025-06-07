package me.timelytask.experimental.notification

class SMSNotificationStrategy extends NotificationStrategy {
  override def sendNotification(notification: Notification): Unit = {
    println(s"Sending SMS to ${notification.recipient} with message: ${notification.message}")
    // -> SMS senden kostet wahrscheinlich
    // wir können hier auch eine andere art notifications zusenden nehmen(GUI Notification oder so)
  }

}
