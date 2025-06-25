package me.timelytask.repository.repositoryImpl
import me.timelytask.repository.simpleReaders.given 
import me.timelytask.model.user.User
import me.timelytask.repository.UserRepository
import simplesql.DataSource

class SqliteUserRepository extends UserRepository {
  val dataSource: DataSource = DataSource.pooled("jbdc:sqlite:TimelyTaskDataStore")

  override def getUserByName(name: String): User = dataSource.transaction {
    sql"""
         SELECT * FROM users WHERE name = $name
       """.readOne[User]
  }

  override def addUser(user: User): Unit = ???

  override def updateName(oldName: String, newName: String): Unit = ???
}
