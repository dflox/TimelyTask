package me.timelytask.experimental.notification

class NotificationContext(private var strategy: NotificationStrategy) {
  def setStrategy(strategy: NotificationStrategy): Unit = {
    this.strategy = strategy
  }

  def send(message: String, recipient: String): Unit = {
    val notification = new SimpleNotification(message, recipient)

    // normale notification als Beispiel
    strategy.sendNotification(notification)

    // deep clone von der notification
    strategy.sendNotification(notification.deepClone())
  }
}
