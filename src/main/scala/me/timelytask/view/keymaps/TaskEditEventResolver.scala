package me.timelytask.view.keymaps

import me.timelytask.model.settings.{EventTypeId, TASKEdit}
import me.timelytask.view.eventHandlers.TaskEditEventContainer
import me.timelytask.view.events.Event
import me.timelytask.view.keymaps.EventResolver
import me.timelytask.view.viewmodel.TaskEditViewModel
import me.timelytask.view.views.{TaskEditView, View}

//Todo: Add Event Mappings.
class TaskEditEventResolver(override protected val eventContainer: TaskEditEventContainer) 
  extends EventResolver[TASKEdit, TaskEditViewModel] {
  override def resolveAndCallEvent(eventType: EventTypeId)
  : Option[Boolean] = {
    eventType match {
//      case EventTypeId("SaveTask") => Some(taskEditView.viewModel.isDefined &
//        taskEditView.saveTask.call(taskEditView.viewModel.get))
      case _ => None
    }
  }
}
