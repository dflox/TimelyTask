package me.timelytask.serviceLayer.servicelayerImpl

import com.github.nscala_time.time.Imports
import com.softwaremill.macwire.wire
import me.timelytask.model.deadline.Deadline
import me.timelytask.model.task.*
import me.timelytask.repository.TaskRepository
import me.timelytask.repository.repositoryImpl.SqliteTaskRepository
import me.timelytask.serviceLayer.{ServiceModule, TaskService, UpdateService}
import me.timelytask.util.Publisher
import simplesql.DataSource

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TaskServiceImpl(serviceModule: ServiceModule, dataSource: DataSource)
  extends TaskService {
  private val taskRepository: TaskRepository = wire[SqliteTaskRepository]
  
  override def updateName(userName: String, taskUUID: UUID, newName: String): Unit = {
      val task = taskRepository.getTaskById(userName, taskUUID)
      val updatedTask = task.withName(newName)
      taskRepository.addTask(userName, updatedTask)
      serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def newTask(userName: String, task: Task): Unit = {
    taskRepository.addTask(userName, task)
  }

  override def deleteTask(userName: String, taskUUID: UUID): Unit = ???

  override def updateDescription(userName: String, taskUUID: UUID, newDescription: String): Unit = ???

  override def updateScheduleDate(userName: String, taskUUID: UUID, newScheduleDate: Imports.DateTime): Unit = ???

  override def updateState(userName: String, taskUUID: UUID, newState: UUID): Unit = ???

  override def updatePriority(userName: String, taskUUID: UUID, newPriority: UUID): Unit = ???

  override def addTag(userName: String, taskUUID: UUID, tagUUID: UUID): Unit = ???

  override def removeTag(userName: String, taskUUID: UUID, tagUUID: UUID): Unit = ???

  override def updateDeadline(userName: String, taskUUID: UUID, newDeadline: Option[Deadline]): Unit = ???

  override def updateTedDuration(userName: String, taskUUID: UUID, newTedDuration: Option[Imports.Period]): Unit = ???

  override def addDependentTask(userName: String, taskUUID: UUID, dependentTaskUUID: UUID): Unit = ???

  override def removeDependentTask(userName: String, taskUUID: UUID, dependentTaskUUID: UUID): Unit = ???

  override def updateReoccurring(userName: String, taskUUID: UUID, newReoccurring: Boolean): Unit = ???

  override def updateRecurrenceInterval(userName: String, taskUUID: UUID, recurrenceInterval: Option[Imports.Period]): Unit = ???

  override def updateRealDuration(userName: String, taskUUID: UUID, newRealDuration: Option[Imports.Period]): Unit = ???

  override def loadAllTasks(userName: String): Seq[Task] = ???
}
