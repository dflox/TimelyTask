package me.timelytask.model.state

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Task

trait TaskState {
  def start(task: Task): Task
  def complete(task: Task): Task
  def cancel(task: Task): Task
  def extendDeadline(task: Task, extension: Period): Task
}