package me.timelytask.view.events

import me.timelytask.model.Task
import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
import me.timelytask.model.utility.InputError
import me.timelytask.view.viewmodel.elemts.{FocusDirection, Focusable}

// Focus Events

case class MoveFocusCalendarView(handler: Handler[FocusDirection],
                                 isPossible: FocusDirection => Option[InputError])
  extends Event[FocusDirection](handler, isPossible)

case class MoveFocusTaskEditView(handler: Handler[FocusDirection],
                                 isPossible: FocusDirection => Option[InputError])
  extends Event[FocusDirection](handler, isPossible)

case object MoveFocus extends TypeSensitiveEventCompanion[Event[FocusDirection], FocusDirection] {
  override protected def create[T <: ViewType](handler: Handler[FocusDirection],
                                               isPossible: FocusDirection => Option[InputError])
  : Event[FocusDirection]
  = null.asInstanceOf[T] match {
    case _: CALENDAR => MoveFocusCalendarView(handler, isPossible)
    case _: TASKEdit => MoveFocusTaskEditView(handler, isPossible)
  }
}

case class SetFocusToTaskCalendarView(handler: Handler[Task],
                                 isPossible: Task => Option[InputError])
  extends Event[Task](handler, isPossible)

case class SetFocusToTaskTaskEditView(handler: Handler[Task],
                                 isPossible: Task => Option[InputError])
  extends Event[Task](handler, isPossible)

case object SetFocusTo extends TypeSensitiveEventCompanion[Event[Task], Task] {
  override protected def create[T <: ViewType](handler: Handler[Task],
                                               isPossible: Task => Option[InputError])
  : Event[Task]
  = null.asInstanceOf[T] match {
    case _: CALENDAR => SetFocusToTaskCalendarView(handler, isPossible)
    case _: TASKEdit => SetFocusToTaskTaskEditView(handler, isPossible)
  }
}