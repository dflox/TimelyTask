package me.timelytask.model.state

import me.timelytask.model.Task

trait TaskState {
  def start(task: Task): Unit
  def complete(task: Task): Unit
  def cancel(task: Task): Unit
}