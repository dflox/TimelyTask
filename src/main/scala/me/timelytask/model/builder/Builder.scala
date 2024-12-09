// src/main/scala/me/timelytask/model/Builder.scala
package me.timelytask.model.builder

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.{Deadline, Task}
import me.timelytask.model.state.*

import java.util.UUID
import scala.collection.immutable.HashSet

trait Builder {
  def setName(name: String): Builder
  def setDescription(description: String): Builder
  def setPriority(priority: UUID): Builder
  def setTags(tags: HashSet[UUID]): Builder
  def setDeadline(deadline: Deadline): Builder
  def setScheduleDate(scheduleDate: DateTime): Builder
  def setState(state: TaskState): Builder
  def setTedDuration(tedDuration: Period): Builder
  def setDependentOn(dependentOn: HashSet[UUID]): Builder
  def setReoccurring(reoccurring: Boolean): Builder
  def setRecurrenceInterval(recurrenceInterval: Period): Builder
  def build(): Task
}