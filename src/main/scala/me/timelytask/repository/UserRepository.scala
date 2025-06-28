package me.timelytask.repository

import me.timelytask.model.user.User

trait UserRepository {
  def getUser(userName: String): User
  def addUser(user: User): Unit
  def removeUser(userName: String): Unit
  def updateName(oldName: String, newName: String): Unit
  def userExists(userName: String): Boolean
}
