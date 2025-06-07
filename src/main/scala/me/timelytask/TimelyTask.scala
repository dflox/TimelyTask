package me.timelytask

import me.timelytask.core.ApplicationCore

@main
def main(): Unit = {
  val timelyTask = ApplicationCore()
  timelyTask.run()
}