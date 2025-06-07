package me.timelytask.view.keymaps

import me.timelytask.model.settings.{EventTypeId, TASKEdit}
import me.timelytask.view.events.container.TaskEditEventContainer
import me.timelytask.view.viewmodel.TaskEditViewModel

//Todo: Add Event Mappings.
class TaskEditEventResolver(override protected val eventContainer: TaskEditEventContainer) 
  extends EventResolver[TASKEdit, TaskEditViewModel] {
  override def resolveAndCallEvent(eventType: EventTypeId)
  : Boolean = {
    given Conversion[Unit, Boolean]{def apply(unit: Unit): Boolean = true}
    eventType match {
//      case EventTypeId("SaveTask") => Some(taskEditView.viewModel.isDefined &
//        taskEditView.saveTask.call(taskEditView.viewModel.get))
      case _ => false
    }
  }
}
