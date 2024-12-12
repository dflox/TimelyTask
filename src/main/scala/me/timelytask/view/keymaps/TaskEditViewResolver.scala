package me.timelytask.view

import me.timelytask.model.settings.{EventTypeId, TASKEdit}
import me.timelytask.view.events.Event
import me.timelytask.view.keymaps.EventResolver
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.views.TaskEditView

class TaskEditViewResolver extends EventResolver[TASKEdit, TaskEditViewModel, TaskEditView[?]] {
  override def resolveEvent[Args](eventType: EventTypeId, view: TaskEditView[?])
  : Option[Event[Args]] = {
    // Type-safe casting helper
    def castIfPossible[T](value: Any): Option[T] =
      value match
        case t: T => Some(t)
        case _ => None

    eventType match {
      case EventTypeId("SaveTask") => castIfPossible[Event[Args]](view.saveTask)
      case _ => None
    }
  }
}
