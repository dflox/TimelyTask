package me.timelytask.view.events

import me.timelytask.model.Task

case class SaveTask(handler: Handler[Task],
                    argumentProvider: ArgumentProvider[Task],
                    isPossible: () => Boolean)
  extends Event[Task](handler, argumentProvider, isPossible)

case object SaveTask extends EventCompanion[SaveTask, Task] {
  override protected def create(argumentProvider: ArgumentProvider[Task],
                                isPossible: () => Boolean): SaveTask = SaveTask(handler.get,
    argumentProvider, isPossible)
}