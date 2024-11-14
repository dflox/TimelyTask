package me.timelytask.view.viewmodel

import com.github.nscala_time.time.Imports.LocalTime
import me.timelytask.model.{Model, Task}

case class TaskModel(model: Model) extends ViewModel {
  val task: Task = model.tasks.head
  val properties: List[(String, Any)] = List(
    "Description" -> task.description,
    "Priority" -> task.priority,
    "Tags" -> task.tags,
    "Deadline" -> task.deadline,
    "Schedule Date" -> task.scheduleDate,
    "State" -> task.state,
    "TED Duration" -> task.tedDuration,
    "Dependent On" -> task.dependentOn,
    "Reoccurring" -> task.reoccurring,
    "Recurrence Interval" -> task.recurrenceInterval
  )
  val longestProperty: String = "Recurrence Interval" + ": "

}