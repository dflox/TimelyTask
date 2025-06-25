package me.timelytask.model.user

case class User(var name: String)

case object User {
  val default: User = User("TestUser")
}