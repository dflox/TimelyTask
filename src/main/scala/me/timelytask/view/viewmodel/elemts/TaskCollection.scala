package me.timelytask.view.viewmodel.elemts

import me.timelytask.model.Task

class TaskCollection(val tasks: List[Task]) extends Focusable {
  override def toString: String = {
    tasks.map(_.name).mkString(", ")
  }
}
