package me.timelytask

@main
def main(): Unit = {
  val timelytask = CoreApplication()
  timelytask.validateSetup()
  timelytask.run()
}