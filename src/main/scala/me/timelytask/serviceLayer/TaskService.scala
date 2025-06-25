package me.timelytask.serviceLayer
import java.util.UUID
import scala.concurrent.Future

trait TaskService {
  def updateName(userName: String, taskUUID: UUID, newName: String): Unit
}
