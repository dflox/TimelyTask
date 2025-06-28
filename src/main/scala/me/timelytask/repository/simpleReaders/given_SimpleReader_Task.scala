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

  override def readIdx(results: ResultSet, idx: Int): Task = {
    Task(
      name = results.getString(idx + 1),
      description = results.getString(idx + 2),
      uuid = results.getObject(idx, classOf[UUID]),
      priority = results.getObject(idx + 3, classOf[UUID]) match {
        case null => None
        case uuid: java.util.UUID => Some(uuid)
      },
      deadline = Deadline(
        date = results.getObject(idx + 4, classOf[DateTime]),
        initialDate = results.getObject(idx + 5, classOf[DateTime]) match {
          case null => None
          case date: DateTime => Some(date)
        },
        completionDate = results.getObject(idx + 6, classOf[DateTime]) match {
          case null => None
          case date: DateTime => Some(date)
        }
      ),
      scheduleDate = DateTime.parse(results.getString(idx + 7)),
      state = results.getObject(idx + 8, classOf[UUID]) match {
        case null => None
        case uuid: java.util.UUID => Some(uuid)
      },
      tedDuration = Period.parse(results.getString(idx + 9)),
      reoccurring = results.getBoolean(idx + 10),
      recurrenceInterval = results.getObject(idx + 11, classOf[Period]),
      realDuration = results.getObject(idx + 12, classOf[Period]) match {
        case null => None
        case period: Period => Some(period)
      }
    )
  }

  override def readName(result: ResultSet, name: String): Task = {
    Task(
      name = result.getString(name + "_name"),
      description = result.getString(name + "_description"),
      uuid = result.getObject(name + "_id", classOf[UUID]),
      priority = result.getObject(name + "_priority", classOf[UUID]) match {
        case null => None
        case uuid: java.util.UUID => Some(uuid)
      },
      deadline = Deadline(
        date = result.getObject(name + "_deadline_date", classOf[DateTime]),
        initialDate = result.getObject(name + "_deadline_initialDate", classOf[DateTime]) match {
          case null => None
          case date: DateTime => Some(date)
        },
        completionDate = result.getObject(name + "_deadline_completionDate", classOf[DateTime]) match {
          case null => None
          case date: DateTime => Some(date)
        }
      ),
      scheduleDate = DateTime.parse(result.getString(name + "_scheduleDate")),
      state = result.getObject(name + "_state", classOf[UUID]) match {
        case null => None
        case uuid: java.util.UUID => Some(uuid)
      },
      tedDuration = Period.parse(result.getString(name + "_tedDuration")),
      reoccurring = result.getBoolean(name + "_reoccuring"),
      recurrenceInterval = result.getObject(name + "_recurrenceInterval", classOf[Period]),
      realDuration = result.getObject(name + "_realDuration", classOf[Period]) match {
        case null => None
        case period: Period => Some(period)
      }
    )
  }
}