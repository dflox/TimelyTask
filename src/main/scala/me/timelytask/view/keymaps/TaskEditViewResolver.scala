package me.timelytask.view.keymaps

import me.timelytask.model.settings.{EventTypeId, TASKEdit}
import me.timelytask.view.events.Event
import me.timelytask.view.keymaps.EventResolver
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.views.{TaskEditView, View}

class TaskEditViewResolver extends EventResolver[TASKEdit, TaskEditViewModel,
  View[TASKEdit, TaskEditViewModel, ?]] {
  override def resolveAndCallEvent(eventType: EventTypeId, view: View[TASKEdit, 
    TaskEditViewModel, ?])
  : Option[Boolean] = {
    view match {
      case taskEditView: TaskEditView[?] => callEvent(eventType, taskEditView)
      case _ => None
    }
  }

  private def callEvent(eventType: EventTypeId, taskEditView: TaskEditView[?]): Option[Boolean] = {
    eventType match {
      case EventTypeId("SaveTask") => Some(taskEditView.viewModel.isDefined &
        taskEditView.saveTask.call(taskEditView.viewModel.get))
      case _ => None
    }
  }
}
