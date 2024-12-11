package me.timelytask.view.views

import me.timelytask.model.settings.TASKEdit
import me.timelytask.view.events.*
import me.timelytask.view.viewmodel.{TaskEditViewModel, ViewModel}

trait TaskEditView extends View[TASKEdit, TaskEditViewModel] {
  val saveTask: SaveTask = SaveTask.createEvent

  val moveFocus: MoveFocus = MoveFocus.createEvent
}
