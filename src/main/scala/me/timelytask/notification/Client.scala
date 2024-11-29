// src/main/scala/me/timelytask/notification/Client.scala
package me.timelytask.notification

object Client extends App {
  val emailStrategy = new EmailNotificationStrategy
  val smsStrategy = new SMSNotificationStrategy

  val context = new NotificationContext(emailStrategy)
  context.send("Hello via Email!", "email@example.com")

  context.setStrategy(smsStrategy)
  context.send("Hello via SMS!", "123-456-7890")
}