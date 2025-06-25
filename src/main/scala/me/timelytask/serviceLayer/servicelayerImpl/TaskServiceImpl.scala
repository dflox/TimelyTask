package me.timelytask.serviceLayer.servicelayerImpl

import com.softwaremill.macwire.wire
import me.timelytask.model.task.*
import me.timelytask.repository.TaskRepository
import me.timelytask.repository.repositoryImpl.SqliteTaskRepository
import me.timelytask.serviceLayer.TaskService
import me.timelytask.util.Publisher
import me.timelytask.util.extensions.replaceOne

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TaskServiceImpl(taskPublisher: Publisher[List[Task]])
  extends TaskService {
  private val taskRepository: TaskRepository = wire[SqliteTaskRepository]
  
  override def updateName(userName: String, taskUUID: UUID, newName: String): Unit = {
      val task = taskRepository.getTaskById(userName, taskUUID)
      val updatedTask = task.withName(newName)
      taskRepository.save(userName, updatedTask)
      taskPublisher.getValue match {
        case Some(tasks) =>
          taskPublisher.update(Some(tasks.replaceOne((t: Task) => t.uuid ==
            taskUUID, updatedTask)))
        case None =>
          taskPublisher.update(Some(List(updatedTask)))
      }
  }
}
