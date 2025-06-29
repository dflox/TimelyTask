package me.timelytask.repository.repositoryImpl

import me.timelytask.model.task.Task
import me.timelytask.model.task.*
import me.timelytask.repository.TaskRepository
import me.timelytask.repository.simpleReaders.given
import me.timelytask.util.extensions.simplesql.*
import simplesql.*

import java.util.UUID
import scala.collection.immutable.HashSet

class SqliteTaskRepository(ds: DataSource) extends TaskRepository {

  private def createTaskTable(): Connection ?=> Int = {
    sql"""
        CREATE TABLE IF NOT EXISTS tasks(
          userid TEXT,
          id TEXT NOT NULL,
          name TEXT NOT NULL,
          description TEXT,
          priority TEXT,
          deadline_date TEXT,
          deadline_initialDate TEXT,
          deadline_completionDate TEXT,
          scheduleDate TEXT,
          state TEXT,
          tedDuration TEXT,
          reoccurring BOOLEAN,
          recurrenceInterval TEXT,
          realDuration TEXT,
          PRIMARY KEY (userid, id),
          FOREIGN KEY (userid) REFERENCES users(name) ON UPDATE CASCADE ON DELETE CASCADE
        )
       """.write()
  }

  private def createTagAssignmentTable(): Connection ?=> Int = {
    // FOREIGN KEY (tagId) REFERENCES tags(id) ON UPDATE CASCADE ON DELETE CASCADE
    sql"""
         CREATE TABLE IF NOT EXISTS task_tags(
         userId TEXT,
         taskId TEXT,
         tagId TEXT,
         PRIMARY KEY (userId, taskId, tagId),
         FOREIGN KEY (userId) REFERENCES users(name) ON UPDATE CASCADE ON DELETE CASCADE,
         FOREIGN KEY (taskId) REFERENCES tasks(id) ON UPDATE CASCADE ON DELETE CASCADE
         )
       """.write()
  }

  private def createDependentOnTable(): Connection ?=> Int = {
    sql"""
         CREATE TABLE IF NOT EXISTS task_dependencies(
         userId TEXT,
         taskId TEXT,
         dependentOnId TEXT,
         PRIMARY KEY (userId, taskId, dependentOnId),
         FOREIGN KEY (userId) REFERENCES users(name) ON UPDATE CASCADE ON DELETE CASCADE,
         FOREIGN KEY (taskId) REFERENCES tasks(id) ON UPDATE CASCADE ON DELETE CASCADE,
         FOREIGN KEY (dependentOnId) REFERENCES tasks(id) ON UPDATE CASCADE ON DELETE CASCADE
         )
       """.write()
  }

  private def updateTags(
      userName: String,
      taskId: UUID,
      tags: Set[UUID]
    ): Unit = {
    ds.transactionWithForeignKeys {
      createTagAssignmentTable()
      sql"""
        DELETE FROM task_tags WHERE taskId = ${taskId.toString} AND tagId NOT IN (${tags
          .map(_.toString)
          .mkString(",")})
       """.write()
      tags.foreach { tagId =>
        sql"""
          INSERT OR IGNORE INTO task_tags(userId, taskId, tagId) VALUES($userName, ${taskId
          .toString}, $tagId.toString)
         """.write()
      }
    }
  }

  private def updateDependentTasks(
      userName: String,
      taskId: UUID,
      dependentTasks: Set[UUID]
    ): Unit = {
    ds.transactionWithForeignKeys {
      createDependentOnTable()
      sql"""
        DELETE FROM task_dependencies WHERE taskId = ${taskId.toString} AND dependentOnId NOT IN
        (${dependentTasks.map(_.toString).mkString(",")})
       """.write()
      dependentTasks.foreach { dependentTaskId =>
        sql"""
          INSERT OR IGNORE INTO task_dependencies(userName, taskId, dependentOnId) VALUES
          ($userName, ${taskId.toString}, ${dependentTaskId.toString})
         """.write()
      }
    }
  }

  private def getTagsForTask(userName: String, taskId: UUID): Set[UUID] = {
    ds.transactionWithForeignKeys {
      createTagAssignmentTable()
      sql"""
        SELECT tagId FROM task_tags WHERE taskId = ${taskId.toString} AND userId = $userName
       """.read[UUID].toSet
    }
  }

  private def getDependentTasksForTask(userName: String, taskId: UUID): Set[UUID] = {
    ds.transactionWithForeignKeys {
      createDependentOnTable()
      sql"""
        SELECT dependentOnId FROM task_dependencies WHERE taskId = ${taskId.toString} AND userId = 
        $userName
       """.read[UUID].toSet
    }
  }

  override def getTaskById(userName: String, taskId: UUID): Task = {
    ds.transactionWithForeignKeys {
      createTaskTable()
      val result = sql"""
          SELECT * FROM tasks WHERE id = ${taskId.toString} AND userid = $userName
       """.readOne[Task]
      result
        .withTags(HashSet.from(getTagsForTask(userName, taskId)))
        .withDependents(
          HashSet.from(getDependentTasksForTask(userName, taskId))
        )
    }
  }

  override def addTask(userName: String, task: Task): Unit = {
    ds.transactionWithForeignKeys {
      createTaskTable()
      sql"""
        INSERT INTO tasks(userid, id, name, description, priority, deadline_date,
        deadline_initialDate,
                          deadline_completionDate, scheduleDate, state, tedDuration, reoccurring,
                          recurrenceInterval, realDuration)
        VALUES($userName,
            ${task.uuid.toString},
            ${task.name},
            ${task.description},
            ${task.priority.getOrElse("").toString},
            ${task.deadline.date.toString},
            ${task.deadline.initialDate.getOrElse("").toString},
            ${task.deadline.completionDate.getOrElse("").toString},
            ${task.scheduleDate.toString},
            ${task.state.getOrElse("").toString},
            ${task.tedDuration.toString},
            ${task.reoccurring},
            ${task.recurrenceInterval.toString},
            ${task.realDuration.getOrElse("").toString})
       """.write()
    }
    updateTags(userName, task.uuid, task.tags)
    updateDependentTasks(userName, task.uuid, task.dependentOn)
  }

  override def getAllTasks(userName: String): Seq[Task] = {
    ds.transactionWithForeignKeys {
      createTaskTable()
      createTagAssignmentTable()
      createDependentOnTable()
      val result = sql"""
        SELECT * FROM tasks WHERE userid = $userName
       """.read[Task]
      result.map { task =>
        task
          .withTags(HashSet.from(getTagsForTask(userName, task.uuid)))
          .withDependents(
            HashSet.from(getDependentTasksForTask(userName, task.uuid))
          )
      }
    }
  }

  override def deleteTask(userName: String, taskId: UUID): Unit =
    ds.transactionWithForeignKeys {
      createTaskTable()
      createTagAssignmentTable()
      createDependentOnTable()
      sql"""
            DELETE FROM tasks WHERE id = ${taskId.toString} AND userid = $userName
       """.write()
    }

  override def updateTask(
      userName: String,
      taskId: UUID,
      updatedTask: Task
    ): Unit = {
    ds.transactionWithForeignKeys {
      createTaskTable()
      sql"""
        UPDATE tasks SET 
          name = ${updatedTask.name},
          description = ${updatedTask.description},
          priority = ${updatedTask.priority.getOrElse("").toString},
          deadline_date = ${updatedTask.deadline.date.toString},
          deadline_initialDate = ${updatedTask.deadline.initialDate
          .getOrElse("")
          .toString},
          deadline_completionDate = ${updatedTask.deadline.completionDate
          .getOrElse("")
          .toString},
          scheduleDate = ${updatedTask.scheduleDate.toString}, 
          state = ${updatedTask.state.getOrElse("").toString},
          tedDuration = ${updatedTask.tedDuration.toString},
          reoccurring =${updatedTask.reoccurring},
          recurrenceInterval = ${updatedTask.recurrenceInterval.toString},
          realDuration = ${updatedTask.realDuration.getOrElse("").toString}
        WHERE id = ${taskId.toString} AND userid = $userName
       """.write()
    }
    updateTags(userName, taskId, updatedTask.tags)
    updateDependentTasks(userName, taskId, updatedTask.dependentOn)
  }
}
