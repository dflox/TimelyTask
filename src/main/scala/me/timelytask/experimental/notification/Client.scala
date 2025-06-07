// src/main/scala/me/timelytask/notification/Client.scala
package me.timelytask.experimental.notification

object Client {
  def run(): Unit = {
    val emailStrategy = new EmailNotificationStrategy
    val smsStrategy = new SMSNotificationStrategy

    val context = new NotificationContext(emailStrategy)
    context.send("Hello via Email!", "email@example.com")

    context.setStrategy(smsStrategy)
    context.send("Hello via SMS!", "123-456-7890")
  }
}