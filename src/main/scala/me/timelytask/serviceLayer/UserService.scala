package me.timelytask.serviceLayer

import me.timelytask.model.user.User

trait UserService {
  def getUser(userName: String): User
  def addUser(userName: String): Unit
  def removeUser(userName: String): Unit
}
