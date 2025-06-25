package me.timelytask.repository.repositoryImpl
import com.github.nscala_time.time
import me.timelytask.repository.simpleReaders.given
import com.github.nscala_time.time.Imports
import me.timelytask.model.deadline.Deadline
import me.timelytask.model.task.Task
import me.timelytask.repository.TaskRepository
import simplesql.*

import java.util.UUID
import scala.concurrent.Future

class SqliteTaskRepository extends TaskRepository {
  val dataSource: DataSource = DataSource.pooled("jbdc:sqlite:TimelyTaskDataStore")

  private def createTaskTable: Unit = dataSource.transaction {
    sql"""
        CREATE TABLE IF NOT EXIST tasks(
          userid TEXT FOREIGN KEY REFERENCES users(name),
          id BLOB NOT NULL,
          name TEXT NOT NULL,
          description TEXT,
          priority BLOB,
          deadline BLOB,
          scheduleDate TEXT,
          state BLOB,
          tedDuration TEXT,
          reoccurring BOOLEAN,
          recurrenceInterval TEXT,
          realDuration TEXT,
          PRIMARY KEY (id, userid)
        )
       """
  }

  private def createTagAssignmentTable: Unit = dataSource.transaction {
    sql"""
         CREATE TABLE IF NOT EXISTS task_tags(
         taskId BLOB FOREIGN KEY REFERENCES tasks(id),
         tagId BLOB FOREIGN KEY REFERENCES tags(id),
         PRIMARY KEY (taskId, tagId)
         )
       """
  }

  private def createDependentOnTable: Unit = dataSource.transaction {
    sql"""
         CREATE TABLE IF NOT EXISTS task_dependencies(
         taskId BLOB FOREIGN KEY REFERENCES tasks(id),
         dependentOnId BLOB FOREIGN KEY REFERENCES tasks(id),
         PRIMARY KEY (taskId, dependentOnId)
         )
       """
  }

  override def getTaskById(userName: String, taskId: UUID): Task = dataSource.transaction {
    sql"""
          SELECT * FROM tasks WHERE id = $taskId AND userid = $userName
       """.readOne[Task]
  }
  
  override def save(userName: String, task: Task): Unit = ???

  override def updateTitle(userName: String, taskId: UUID, newTitle: String): Unit = ???

  override def updateDescription(userName: String, taskId: UUID, newDescription: String): Unit = ???

  override def updatePriority(userName: String, taskId: UUID, newPriority: UUID): Unit = ???

  override def updateTags(userName: String, taskId: UUID, newTags: Set[UUID]): Unit = ???

  override def updateDeadline(userName: String, taskId: UUID, newDeadline: Deadline): Unit = ???

  override def updateScheduleDate(userName: String, taskId: UUID, newScheduleDate: time.Imports.DateTime): Unit = ???

  override def updateState(userName: String, taskId: UUID, newState: Option[UUID]): Unit = ???

  override def updateTedDuration(userName: String, taskId: UUID, newTedDuration: time.Imports.Period): Unit = ???

  override def updateDependentOn(userName: String, taskId: UUID, newDependentOn: Set[UUID]): Unit = ???

  override def updateReoccurring(userName: String, taskId: UUID, newRecurring: Boolean): Unit = ???

  override def updateReoccurrenceInterval(userName: String, taskId: UUID, newReoccurrenceInterval: time.Imports.Period): Unit = ???

  override def updateUUID(userName: String, taskId: UUID, newUUID: UUID): Unit = ???

  override def updateRealDuration(userName: String, taskId: UUID, newRealDuration: Option[time.Imports.Period]): Unit = ???
}