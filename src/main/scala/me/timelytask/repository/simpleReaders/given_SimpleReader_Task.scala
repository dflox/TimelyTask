package me.timelytask.repository.simpleReaders

import com.github.nscala_time.time.Imports.{DateTime, Period}
import me.timelytask.model.deadline.Deadline
import me.timelytask.model.task.Task
import simplesql.SimpleReader

import java.sql.ResultSet
import java.util.UUID

given SimpleReader[Task] with {
  private def throwCorruptedTaskIdError(): Nothing = {
    throw new IllegalArgumentException("Task ID cannot be null. The database might be corrupted.")
  }

  def read(rs: ResultSet): Task = {
    Task(
      name = rs.getString("name"),
      description = rs.getString("description"),
      uuid = rs.getString("id") match {
        case null | "" => throwCorruptedTaskIdError()
        case id: String => UUID.fromString(id)
      },
      priority = rs.getString("priority") match {
        case null | "" => None
        case uuid => Some(UUID.fromString(uuid))
      },
      deadline = Deadline(
        date = DateTime.parse(rs.getString("deadline_date")),
        initialDate = rs.getString("deadline_initialDate") match {
          case null | "" => None
          case date: String => Some(DateTime.parse(date))
        },
        completionDate = rs.getString("deadline_completionDate")  match {
          case null | "" => None
          case date: String => Some(DateTime.parse(date))
        }
      ),
      scheduleDate = DateTime.parse(rs.getString("scheduleDate")),
      state = rs.getString("state") match {
        case null | "" => None
        case uuid => Some(UUID.fromString(uuid))
      },
      tedDuration = Period.parse(rs.getString("tedDuration")),
      reoccurring = rs.getBoolean("reoccuring"),
      recurrenceInterval = Period.parse(rs.getString("recurrenceInterval")),
      realDuration = rs.getString("realDuration") match {
        case null | "" => None
        case period: String => Some(Period.parse(period))
      }
      )
  }

  override def readIdx(results: ResultSet, idx: Int): Task = {
    Task(
      name = results.getString(idx + 2),
      description = results.getString(idx + 3),
      uuid = results.getString(idx + 1) match {
        case null | "" => throwCorruptedTaskIdError()
        case id: String => UUID.fromString(id)
      },
      priority = results.getString(idx + 4) match {
        case null | "" => None
        case uuid => Some(UUID.fromString(uuid))
      },
      deadline = Deadline(
        date = DateTime.parse(results.getString(idx + 5)),
        initialDate = results.getString(idx + 6) match {
          case null | "" => None
          case date: String => Some(DateTime.parse(date))
        },
        completionDate = results.getString(idx + 7) match {
          case null | "" => None
          case date: String => Some(DateTime.parse(date))
        }
      ),
      scheduleDate = DateTime.parse(results.getString(idx + 8)),
      state = results.getString(idx + 9) match {
        case null | "" => None
        case uuid => Some(UUID.fromString(uuid))
      },
      tedDuration = Period.parse(results.getString(idx + 10)),
      reoccurring = results.getBoolean(idx + 11),
      recurrenceInterval = Period.parse(results.getString(idx + 12)),
      realDuration = results.getString(idx + 12) match {
        case null | "" => None
        case period: String => Some(Period.parse(period))
      }
    )
  }

  override def readName(result: ResultSet, name: String): Task = {
    Task(
      name = result.getString(name + "_name"),
      description = result.getString(name + "_description"),
      uuid = result.getString(name + "_id") match {
        case null | "" => throwCorruptedTaskIdError()
        case id: String => UUID.fromString(id)
      },
      priority = result.getString(name + "_priority") match {
        case null | "" => None
        case uuid => Some(UUID.fromString(uuid))
      },
      deadline = Deadline(
        date = DateTime.parse(result.getString(name + "_deadline_date")),
        initialDate = result.getString(name + "_deadline_initialDate") match {
          case null | "" => None
          case date: String => Some(DateTime.parse(date))
        },
        completionDate = result.getString(name + "_deadline_completionDate") match {
          case null | "" => None
          case date: String => Some(DateTime.parse(date))
        }
      ),
      scheduleDate = DateTime.parse(result.getString(name + "_scheduleDate")),
      state = result.getString(name + "_state") match {
        case null | "" => None
        case uuid => Some(UUID.fromString(uuid))
      },
      tedDuration = Period.parse(result.getString(name + "_tedDuration")),
      reoccurring = result.getBoolean(name + "_reoccuring"),
      recurrenceInterval = Period.parse(result.getString(name + "_recurrenceInterval")),
      realDuration = result.getString(name + "_realDuration") match {
        case null | "" => None
        case period: String => Some(Period.parse(period))
      }
    )
  }
}