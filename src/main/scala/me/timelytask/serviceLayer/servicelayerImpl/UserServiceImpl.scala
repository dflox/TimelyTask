package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.user.User
import me.timelytask.serviceLayer.UserService

class UserServiceImpl extends UserService {

  override def getUser(userName: String): User = ???

  override def addUser(userName: String): Unit = ???

  override def removeUser(userName: String): Unit = ???
}
