package me.timelytask.view.events

import me.timelytask.model.Task
import me.timelytask.model.utility.InputError
import me.timelytask.view.viewmodel.elemts.FocusDirection

// Focus Events

case class MoveFocus(handler: Handler[FocusDirection],
                     isPossible: FocusDirection => Option[InputError])
  extends Event[FocusDirection](handler, isPossible)

case object MoveFocus extends EventCompanion[MoveFocus, FocusDirection] {
  override protected def create: MoveFocus = MoveFocus(handler.get, isPossible.get)
}

case class SetFocusTo(handler: Handler[Task],
                      isPossible: Task => Option[InputError])
  extends Event[Task](handler, isPossible)

case object SetFocusTo extends EventCompanion[SetFocusTo, Task] {
  override protected def create: SetFocusTo = SetFocusTo(handler.get, isPossible.get)
}