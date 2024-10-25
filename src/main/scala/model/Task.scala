package model

import com.github.nscala_time.time.Imports.*
import model.settings.DataType

import java.util.UUID
import scala.collection.immutable.HashSet

case class Task(name: String, description: String,
                priority: UUID, tags: HashSet[UUID] = new HashSet[UUID](), deadline: Deadline,
                status: UUID, estimatedDuration: Period, dependentOn: HashSet[UUID] = new HashSet[UUID](),
                reoccurring: Boolean, recurrenceInterval: Period) {

  val uuid: UUID = UUID.randomUUID()
  val realDuration: Option[Period] = None
  val completionDate: Option[DateTime] = None
}