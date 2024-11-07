package me.timelytask.model

import com.github.nscala_time.time.Imports.*

import java.util.UUID
import scala.collection.immutable.HashSet

case class Task(name: String, description: String,
                priority: UUID, tags: HashSet[UUID] = new HashSet[UUID](), deadline: Deadline,
                state: UUID, estimatedDuration: Period, dependentOn: HashSet[UUID] = new HashSet[UUID](),
                reoccurring: Boolean, recurrenceInterval: Period) {

  val uuid: UUID = UUID.randomUUID()
  val realDuration: Option[Period] = None
  val completionDate: Option[DateTime] = None
}

object Task {
  val exampleTask: Task = Task("ExTask", "This is an example task",
    UUID.randomUUID(), HashSet(UUID.randomUUID()), Deadline(DateTime.now(), None, None),
    UUID.randomUUID(), 1.hour, HashSet(UUID.randomUUID()), false, 1.hour)
}