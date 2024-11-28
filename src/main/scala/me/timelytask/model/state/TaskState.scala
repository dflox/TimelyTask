package me.timelytask.model.state

import me.timelytask.model.Task

trait TaskState {
  def start(task: Task): Task
  def complete(task: Task): Task
  def cancel(task: Task): Task
}