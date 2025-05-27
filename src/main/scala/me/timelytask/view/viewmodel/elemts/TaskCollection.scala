package me.timelytask.view.viewmodel.elemts

import com.github.nscala_time.time.Imports.Interval
import me.timelytask.model.Task
import me.timelytask.view.viewmodel.dialogmodel.{DialogModel, OptionDialogModel}

class TaskCollection(tasks: List[Task]) extends Focusable[Task] {
  override val description: String = "TaskCollection"

  def getTasks: List[Task] = tasks

  override val dialogModel: OptionDialogModel[Task] = OptionDialogModel[Task]("Tasks", tasks,
    _.name)

  override def toString: String = {
    tasks.map(_.name).mkString(", ")
  }
}
