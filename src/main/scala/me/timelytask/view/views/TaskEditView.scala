package me.timelytask.view.views

import me.timelytask.model.Task
import me.timelytask.model.settings.TASKEdit
import me.timelytask.view.events.*
import me.timelytask.view.viewmodel.dialogmodel.DialogModel
import me.timelytask.view.viewmodel.{TaskEditViewModel, ViewModel}

trait TaskEditView[RenderType] extends View[TASKEdit, TaskEditViewModel, RenderType] {
}
