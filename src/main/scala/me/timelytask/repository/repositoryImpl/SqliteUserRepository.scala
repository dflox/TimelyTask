package me.timelytask.repository.repositoryImpl

import me.timelytask.model.user.User
import me.timelytask.repository.UserRepository
import me.timelytask.repository.simpleReaders.given
import me.timelytask.util.extensions.simplesql.*
import simplesql.{Connection, DataSource}

class SqliteUserRepository(ds: DataSource) extends UserRepository {

  private def createUserTable(): Connection ?=> Int = {
    sql"""
            CREATE TABLE IF NOT EXISTS users (name TEXT PRIMARY KEY)
         """.write()
  }

  override def getUser(userName: String): User = {
    ds.transactionWithForeignKeys {
      createUserTable()
      sql"""
            SELECT name FROM users WHERE name = $userName
         """.readOne[User]
    }
  }

  override def addUser(user: User): Unit = {
    ds.transactionWithForeignKeys {
      createUserTable()
      sql"""
          INSERT INTO users (name) VALUES (${user.name})
         """.write()
    }
  }

  override def removeUser(userName: String): Unit = {
    ds.transactionWithForeignKeys {
      createUserTable()
    }
    ds.transactionWithForeignKeys {
      // TODO: Try with run not transaction
      sql"""
            DELETE FROM users WHERE name = $userName
         """.write()
    }
  }

  override def userExists(userName: String): Boolean = {
    ds.transactionWithForeignKeys {
      createUserTable()
      sql"""
            SELECT COUNT(*) FROM users WHERE name = $userName
         """.readOne[Int] > 0
    }
  }

  override def updateName(oldName: String, newName: String): Unit = {
    ds.transactionWithForeignKeys {
      createUserTable()
      sql"""
            UPDATE users SET name = $newName WHERE name = $oldName
         """.write()
    }
  }
}
