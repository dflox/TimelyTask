package me.timelytask.repository.repositoryImpl

import me.timelytask.repository.simpleReaders.given
import me.timelytask.model.user.User
import me.timelytask.repository.UserRepository
import simplesql.DataSource

class SqliteUserRepository(dataSource: DataSource) extends UserRepository {

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

  override def addUser(user: User): Unit = dataSource.transaction {
    createUserTable()
    sql"""
          INSERT INTO users (name) VALUES (${user.name})
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

  override def updateName(oldName: String, newName: String): Unit = dataSource.transaction {
    createUserTable()
    sql"""
            UPDATE users SET name = $newName WHERE name = $oldName
         """.write()
  }
}
