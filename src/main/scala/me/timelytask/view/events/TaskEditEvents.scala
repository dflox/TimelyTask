package me.timelytask.view.events

import me.timelytask.model.Task
import me.timelytask.model.utility.InputError
import me.timelytask.view.viewmodel.TaskEditViewModel

case class SaveTask(handler: Handler[TaskEditViewModel],
                    isPossible: TaskEditViewModel => Option[InputError])
  extends Event[TaskEditViewModel](handler, isPossible)

case object SaveTask extends EventCompanion[SaveTask, TaskEditViewModel] {
  override protected def create: SaveTask = SaveTask(handler.get, isPossible.get)
}

case class CancelTask(handler: Handler[TaskEditViewModel],
                      isPossible: TaskEditViewModel => Option[InputError])
  extends Event[TaskEditViewModel](handler, isPossible)

case object CancelTask extends EventCompanion[CancelTask, TaskEditViewModel] {
  override protected def create: CancelTask = CancelTask(handler.get, isPossible.get)
}