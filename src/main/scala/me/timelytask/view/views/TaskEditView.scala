package me.timelytask.view.views

import me.timelytask.model.settings.TASKEdit
import me.timelytask.view.events.{Event, SaveTask}
import me.timelytask.view.viewmodel.{TaskEditViewModel, ViewModel}

trait TaskEditView extends View[TASKEdit, TaskEditViewModel] {
  def saveTask: Event[TaskEditViewModel] = SaveTask.createEvent
}
