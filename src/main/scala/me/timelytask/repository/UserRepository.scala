package me.timelytask.repository

import me.timelytask.model.user.User

trait UserRepository {
  def getUserByName(name: String): User
  def addUser(user: User): Unit
  def updateName(oldName: String, newName: String): Unit
}
