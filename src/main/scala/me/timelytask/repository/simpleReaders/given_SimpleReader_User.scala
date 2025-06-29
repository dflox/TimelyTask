package me.timelytask.repository.simpleReaders

import me.timelytask.model.user.User
import simplesql.SimpleReader

import java.sql.ResultSet

given SimpleReader[User] with {
  def read(rs: ResultSet): User = {
    User(name = rs.getString("name"))
  }

  override def readIdx(results: ResultSet, idx: Int): User = {
    User(name = results.getString(idx))
  }

  override def readName(result: ResultSet, name: String): User = {
    User(name = result.getString(name))
  }
}