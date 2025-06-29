package me.timelytask.serviceLayer.servicelayerImpl

import com.github.nscala_time.time.Imports
import com.softwaremill.macwire.wireWith
import me.timelytask.model.deadline.Deadline
import me.timelytask.model.task.*
import me.timelytask.repository.TaskRepository
import me.timelytask.repository.repositoryImpl.SqliteTaskRepository
import me.timelytask.serviceLayer.{ServiceModule, TaskService}

import java.util.UUID

class TaskServiceImpl(serviceModule: ServiceModule, taskRepository: TaskRepository)
  extends TaskService {
  
  override def updateName(userName: String, taskUUID: UUID, newName: String): Unit = {
      val task = taskRepository.getTaskById(userName, taskUUID)
      val updatedTask = task.withName(newName)
      taskRepository.addTask(userName, updatedTask)
      serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def newTask(userName: String, task: Task): Unit = {
    taskRepository.addTask(userName, task)
    serviceModule.modelService.loadModel(userName)
  }

  override def deleteTask(userName: String, taskUUID: UUID): Unit = {
    taskRepository.deleteTask(userName, taskUUID)
    serviceModule.modelService.loadModel(userName)
  }

  override def updateDescription(userName: String, taskUUID: UUID, newDescription: String): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.withDescription(newDescription)
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def updateScheduleDate(userName: String, taskUUID: UUID, newScheduleDate: Imports.DateTime): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.withScheduleDate(newScheduleDate)
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def updateState(userName: String, taskUUID: UUID, newState: UUID): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.withState(Some(newState))
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def updatePriority(userName: String, taskUUID: UUID, newPriority: UUID): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.withPriority(Some(newPriority))
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def addTag(userName: String, taskUUID: UUID, tagUUID: UUID): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.withTags(task.tags + tagUUID)
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def removeTag(userName: String, taskUUID: UUID, tagUUID: UUID): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.withTags(task.tags - tagUUID)
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def updateDeadline(userName: String, taskUUID: UUID, newDeadline: Deadline): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.withDeadline(newDeadline)
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def updateTedDuration(userName: String, taskUUID: UUID, newTedDuration: Imports.Period): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.withTedDuration(newTedDuration)
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def addDependentTask(userName: String, taskUUID: UUID, dependentTaskUUID: UUID): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.addDependentOn(dependentTaskUUID)
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def removeDependentTask(userName: String, taskUUID: UUID, dependentTaskUUID: UUID): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.removeDependentOn(dependentTaskUUID)
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def updateReoccurring(userName: String, taskUUID: UUID, newReoccurring: Boolean): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.withReoccurring(newReoccurring)
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def updateRecurrenceInterval(userName: String, taskUUID: UUID, recurrenceInterval: Imports.Period): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.withRecurrenceInterval(recurrenceInterval)
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  override def updateRealDuration(userName: String, taskUUID: UUID, newRealDuration: Option[Imports.Period]): Unit = {
    val task = taskRepository.getTaskById(userName, taskUUID)
    val updatedTask = task.withRealDuration(newRealDuration)
    taskRepository.updateTask(userName, updatedTask.uuid, updatedTask)
    serviceModule.updateService.updateTask(userName, updatedTask)
  }

  private[serviceLayer] override def loadAllTasks(userName: String): Seq[Task] = {
    taskRepository.getAllTasks(userName)
  }
}
