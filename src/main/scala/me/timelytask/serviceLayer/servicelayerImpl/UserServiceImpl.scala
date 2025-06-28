package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.user.User
import me.timelytask.repository.UserRepository
import me.timelytask.repository.simpleReaders.given_SimpleReader_User
import me.timelytask.serviceLayer.{ServiceModule, UserService}
import simplesql.DataSource

class UserServiceImpl(serviceModule: ServiceModule, userRepository: UserRepository) extends
                                                                                UserService {
  override def userExists(userName: String): Boolean = {
    userRepository.userExists(userName)
  }

  override def getUser(userName: String): User = {
    userRepository.getUser(userName)
  }

  override def addUser(userName: String): Unit = {
    val user = User(userName)
    userRepository.addUser(user)
  }

  override def removeUser(userName: String): Unit = {
    userRepository.removeUser(userName)
  }

  override def updateName(oldName: String, newName: String): Unit = {
    userRepository.updateName(oldName, newName)
  }
}