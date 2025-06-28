package me.timelytask.repository.repositoryImpl
import me.timelytask.model.task.Task
import me.timelytask.model.task.*
import me.timelytask.repository.TaskRepository
import me.timelytask.repository.simpleReaders.given
import simplesql.*

import java.util.UUID
import scala.collection.immutable.HashSet

class SqliteTaskRepository(dataSource: DataSource) extends TaskRepository {

  private def createTaskTable: Unit = dataSource.transaction {
    sql"""
        CREATE TABLE IF NOT EXIST tasks(
          userid TEXT FOREIGN KEY REFERENCES users(name) ON UPDATE CASCADE ON DELETE CASCADE,
          id BLOB NOT NULL,
          name TEXT NOT NULL,
          description TEXT,
          priority BLOB,
          deadline_date TEXT,
          deadline_initialDate TEXT,
          deadline_completionDate TEXT,
          scheduleDate TEXT,
          state BLOB,
          tedDuration TEXT,
          reoccurring BOOLEAN,
          recurrenceInterval TEXT,
          realDuration TEXT,
          PRIMARY KEY (id, userid)
        )
       """.write()
  }

  private def createTagAssignmentTable: Unit = dataSource.transaction {
    sql"""
         CREATE TABLE IF NOT EXISTS task_tags(
         taskId BLOB FOREIGN KEY REFERENCES tasks(id) ON UPDATE CASCADE ON DELETE CASCADE,
         tagId BLOB FOREIGN KEY REFERENCES tags(id) ON UPDATE CASCADE ON DELETE CASCADE,
         PRIMARY KEY (taskId, tagId)
         )
       """.write()
  }

  private def createDependentOnTable: Unit = dataSource.transaction {
    sql"""
         CREATE TABLE IF NOT EXISTS task_dependencies(
         taskId BLOB FOREIGN KEY REFERENCES tasks(id) ON UPDATE CASCADE ON DELETE CASCADE,
         dependentOnId BLOB FOREIGN KEY REFERENCES tasks(id) ON UPDATE CASCADE ON DELETE CASCADE,
         PRIMARY KEY (taskId, dependentOnId)
         )
       """.write()
  }
  
  private def updateTags(userName: String, taskId: UUID, tags: Set[UUID]): Unit = dataSource.transaction {
    createTagAssignmentTable
    sql"""
        DELETE FROM task_tags WHERE taskId = $taskId AND tagId NOT IN (${tags.mkString(",")})
       """.write()
    tags.foreach { tagId =>
      sql"""
          INSERT OR IGNORE INTO task_tags(taskId, tagId) VALUES($taskId, $tagId)
         """.write()
    }
  }
  
  private def updateDependentTasks(userName: String, taskId: UUID, dependentTasks: Set[UUID]): Unit = dataSource.transaction {
    createDependentOnTable
    sql"""
        DELETE FROM task_dependencies WHERE taskId = $taskId AND dependentOnId NOT IN (${dependentTasks.mkString(",")})
       """.write()
    dependentTasks.foreach { dependentTaskId =>
      sql"""
          INSERT OR IGNORE INTO task_dependencies(taskId, dependentOnId) VALUES($taskId, $dependentTaskId)
         """.write()
    }
  }
  
  private def getTagsForTask(userName: String, taskId: UUID): Set[UUID] = dataSource.transaction {
    createTagAssignmentTable
    sql"""
        SELECT tagId FROM task_tags WHERE taskId = $taskId
       """.read[UUID].toSet
  }
  
  private def getDependentTasksForTask(userName: String, taskId: UUID): Set[UUID] = dataSource.transaction {
    createDependentOnTable
    sql"""
        SELECT dependentOnId FROM task_dependencies WHERE taskId = $taskId
       """.read[UUID].toSet
  }

  override def getTaskById(userName: String, taskId: UUID): Task = dataSource.transaction {
    createTaskTable
    val result = sql"""
          SELECT * FROM tasks WHERE id = $taskId AND userid = $userName
       """.readOne[Task]
    result.withTags(HashSet.from(getTagsForTask(userName, taskId)))
          .withDependents(HashSet.from(getDependentTasksForTask(userName, taskId)))
  }
  
  override def addTask(userName: String, task: Task): Unit = dataSource.transaction {
    createTaskTable
    sql"""
        INSERT INTO tasks(userid, id, name, description, priority, deadline_date, deadline_initialDate,
                          deadline_completionDate, scheduleDate, state, tedDuration, reoccurring,
                          recurrenceInterval, realDuration)
        VALUES(${userName}, ${task.uuid}, ${task.name}, ${task.description}, ${task.priority},
               ${task.deadline.date.toString}, ${task.deadline.initialDate.getOrElse("").toString},
               ${task.deadline.completionDate.getOrElse("").toString}, ${task.scheduleDate.toString},
               ${task.state}, ${task.tedDuration.toString}, ${task.reoccurring},
               ${task.recurrenceInterval.toString}, ${task.realDuration.getOrElse("").toString})
       """.write()
    updateTags(userName, task.uuid, task.tags)
    updateDependentTasks(userName, task.uuid, task.dependentOn)
  }

  override def getAllTasks(userName: String): Seq[Task] = dataSource.transaction {
    createTaskTable
    val result = sql"""
        SELECT * FROM tasks WHERE userid = $userName
       """.read[Task]
    result.map { task =>
      task.withTags(HashSet.from(getTagsForTask(userName, task.uuid)))
          .withDependents(HashSet.from(getDependentTasksForTask(userName, task.uuid)))
    }
  }

  override def deleteTask(userName: String, taskId: UUID): Unit = dataSource.transaction {
    sql"""
            DELETE FROM task_tags WHERE taskId = $taskId
           """.write()
    sql"""
            DELETE FROM task_dependencies WHERE taskId = $taskId
           """.write()
    sql"""
        DELETE FROM tasks WHERE id = $taskId AND userid = $userName
       """.write()
  }

  override def updateTask(userName: String, taskId: UUID, updatedTask: Task): Unit = dataSource.transaction {
    createTaskTable
    sql"""
        UPDATE tasks SET name = ${updatedTask.name}, description = ${updatedTask.description},
                         priority = ${updatedTask.priority}, deadline_date = ${updatedTask.deadline.date.toString},
                         deadline_initialDate = ${updatedTask.deadline.initialDate.getOrElse("").toString},
                         deadline_completionDate = ${updatedTask.deadline.completionDate.getOrElse("").toString},
                         scheduleDate = ${updatedTask.scheduleDate.toString}, state = ${updatedTask.state},
                         tedDuration = ${updatedTask.tedDuration.toString}, reoccurring = ${updatedTask.reoccurring},
                         recurrenceInterval = ${updatedTask.recurrenceInterval.toString},
                         realDuration = ${updatedTask.realDuration.getOrElse("").toString}
        WHERE id = $taskId AND userid = $userName
       """.write()
    updateTags(userName, taskId, updatedTask.tags)
    updateDependentTasks(userName, taskId, updatedTask.dependentOn)
  }
}