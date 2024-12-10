package me.timelytask.view.viewmodel.elemts

import me.timelytask.model.Task
import com.github.nscala_time.time.Imports.Interval

class TaskCollection(tasks: List[Task], interval: Interval) extends Focusable {
  
  override def toString: String = {
    tasks.map(_.name).mkString(", ")
  }
}
