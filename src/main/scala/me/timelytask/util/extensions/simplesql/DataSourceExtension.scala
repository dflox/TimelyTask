package me.timelytask.util.extensions.simplesql

import simplesql.{Connection, DataSource}

extension (ds: DataSource) {
  def runWithForeignKeys[A](block: Connection ?=> A): A = ds.run {
      initForeignKeys()
      block
    }
    
  def transactionWithForeignKeys[A](block: Connection ?=> A): A = ds.transaction {
      initForeignKeys()
      block
    }
    
  private def initForeignKeys()(using conn: Connection): Unit = {
    val stmt = conn.underlying.createStatement()
    stmt.executeUpdate("PRAGMA foreign_keys = ON")
    stmt.close()
  }
}