package me.timelytask.repository.simpleReaders

import com.github.nscala_time.time.Imports.{DateTime, Period}
import me.timelytask.model.deadline.Deadline
import me.timelytask.model.task.Task
import simplesql.SimpleReader

import java.sql.ResultSet
import java.util.UUID

given SimpleReader[Task] with {
  def read(rs: ResultSet): Task = {
    Task(
      name = rs.getString("name"),
      description = rs.getString("description"),
      uuid = rs.getObject("id", classOf[UUID]),
      priority = rs.getObject("priority", classOf[UUID]) match {
        case null => None
        case uuid: java.util.UUID => Some(uuid)
      },
      deadline = Deadline(
        date = rs.getObject("deadline_date", classOf[DateTime]),
        initialDate = rs.getObject("deadline_initialDate", classOf[DateTime]) match {
          case null => None
          case date: DateTime => Some(date)
        },
        completionDate = rs.getObject("deadline_completionDate", classOf[DateTime]) match {
          case null => None
          case date: DateTime => Some(date)
        }
      ),
      scheduleDate = DateTime.parse(rs.getString("scheduleDate")),
      state = rs.getObject("state", classOf[UUID]) match {
        case null => None
        case uuid: java.util.UUID => Some(uuid)
      },
      tedDuration = Period.parse(rs.getString("tedDuration")),
      reoccurring = rs.getBoolean("reoccuring"),
      recurrenceInterval = rs.getObject("recurrenceInterval", classOf[Period]),
        realDuration = rs.getObject("realDuration", classOf[Period]) match {
          case null => None
          case period: Period => Some(period)
        }
      )
  }

  override def readIdx(results: ResultSet, idx: Int): Task = ???

  override def readName(result: ResultSet, name: String): Task = ???
}