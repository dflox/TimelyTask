package me.timelytask.view.viewmodel

import com.github.nscala_time.time.Imports.LocalTime
import me.timelytask.model.builder.TaskBuilder
import me.timelytask.model.settings.*
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.elemts.{FocusElementGrid, TextField}

import java.util.UUID

case class TaskEditViewModel(taskID: UUID, task: Task = Task(),
                             lastView: ViewType, isNewTask: Boolean = false,
                             protected var focusElementGrid: Option[FocusElementGrid] = None)
                            (using modelPublisher: Publisher[Model])
  extends ViewModel[TASKEdit](modelPublisher) {

  focusElementGridInit()

  private def focusElementGridInit(): Unit = {
    if (focusElementGrid.isDefined) return
    val elements = Vector(properties.map((name, value) => Some(TextField(name, value
      .toString))).toVector)
    focusElementGrid = Some(FocusElementGrid(elements, elements.head.head))
  }

  def getFocusElementGrid: Option[FocusElementGrid] = focusElementGrid

  val taskBuilder: TaskBuilder = TaskBuilder(task)
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