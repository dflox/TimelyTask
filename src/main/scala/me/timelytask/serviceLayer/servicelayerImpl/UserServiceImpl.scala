package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.user.User
import me.timelytask.repository.simpleReaders.given_SimpleReader_User
import me.timelytask.serviceLayer.UserService
import simplesql.DataSource

class UserServiceImpl(dataSource: DataSource) extends UserService {

  private def createUserTable(): Unit = dataSource.transaction {
    sql"""
          CREATE TABLE IF NOT EXISTS users (name TEXT PRIMARY KEY)
       """
  }

  override def getUser(userName: String): User = dataSource.transaction {
    createUserTable()
    sql"""
          SELECT name FROM users WHERE name = $userName
       """.readOne[User]
  }

  override def addUser(userName: String): Unit = dataSource.transaction {
    createUserTable()
    sql"""
          INSERT INTO users (name) VALUES ($userName)
       """.write()
  }

  override def removeUser(userName: String): Unit = dataSource.transaction {
    createUserTable()
    sql"""
          DELETE FROM users WHERE name = $userName
       """.write()
  }

  override def userExists(userName: String): Boolean = dataSource.transaction {
    createUserTable()
    sql"""
          SELECT COUNT(*) FROM users WHERE name = $userName
       """.readOne[Int] > 0
  }
}