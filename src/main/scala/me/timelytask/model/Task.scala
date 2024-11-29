// src/main/scala/me/timelytask/model/Task.scala
package me.timelytask.model

import me.timelytask.controller.mediator.{Mediator, TaskMediator}
import com.github.nscala_time.time.Imports.*
import me.timelytask.controller.TaskController
import me.timelytask.model.state.{OpenState, TaskState}
import me.timelytask.view.viewmodel.{ViewModel, ViewModelStatus}
import me.timelytask.model.settings.activeViewPublisher
import me.timelytask.TimelyTask.viewModelPublisher
import me.timelytask.TimelyTask.modelPublisher
import me.timelytask.model.builder.TaskBuilder

import java.util.UUID
import scala.collection.immutable.HashSet

implicit val defaultMediator: Mediator = new TaskMediator(new TaskController, new ViewModelStatus)

case class Task(name: String,
                description: String,
                priority: UUID,
                tags: HashSet[UUID] = new HashSet[UUID](),
                deadline: Deadline,
                scheduleDate: DateTime,
                state: TaskState = new OpenState,
                tedDuration: Period,
                dependentOn: HashSet[UUID] = new HashSet[UUID](),
                reoccurring: Boolean,
                recurrenceInterval: Period,
                mediator: Mediator = defaultMediator) {

  val uuid: UUID = UUID.randomUUID()
  val realDuration: Option[Period] = None
  val completionDate: Option[DateTime] = None

  def start(): Task = {

    // notify the mediator that the task has started (Mediator Pattern)
    mediator.notify(this, "TaskStarted")
    // change the state of the task to started (State Pattern)
    state.start(this)
  }

  def complete(): Task = {
    mediator.notify(this, "TaskCompleted")
    state.complete(this)
  }

  def cancel(): Task = {
    mediator.notify(this, "TaskCancelled")
    state.cancel(this)
  }
  def extendDeadline(extension: Period): Task = state.extendDeadline(this, extension)
}

object Task {


  // wird jetzt mit Builder erstellt (Builder Pattern)
  val exampleTask : Task = TaskBuilder()
    .setName("Example Task")
    .setDescription("This is an example task")
    .setPriority(UUID.randomUUID())
    .setTags(HashSet(UUID.randomUUID()))
    .setDeadline(Deadline(DateTime.now(), None, None))
    .setScheduleDate(DateTime.now())
    .setState(new OpenState)
    .setTedDuration(1.hour.toPeriod)
    .setDependentOn(HashSet())
    .setReoccurring(false)
    .setRecurrenceInterval(1.week.toPeriod)
    .build()

  val emptyTask : Task = TaskBuilder()
    .setName("")
    .setDescription("")
    .setPriority(UUID.randomUUID())
    .setTags(HashSet())
    .setDeadline(Deadline(DateTime.now(), None, None))
    .setScheduleDate(DateTime.now())
    .setState(new OpenState)
    .setTedDuration(0.seconds.toPeriod)
    .setDependentOn(HashSet())
    .setReoccurring(false)
    .setRecurrenceInterval(0.seconds.toPeriod)
    .build()
}