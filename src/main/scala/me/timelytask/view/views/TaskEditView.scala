package me.timelytask.view.views

import me.timelytask.model.Task
import me.timelytask.model.settings.TASKEdit
import me.timelytask.view.events.*
import me.timelytask.view.viewmodel.dialogmodel.OptionDialogModel
import me.timelytask.view.viewmodel.{TaskEditViewModel, ViewModel}

trait TaskEditView[RenderType] extends View[TASKEdit, TaskEditViewModel, RenderType] {
  val saveTask: SaveTask = SaveTask.createEvent
  val cancelTask: CancelTask = CancelTask.createEvent

  def renderOptionDialog: (optionDialogModel: Option[OptionDialogModel[Task]],
                           renderType: Option[RenderType]) => Option[Task]
  
  protected def interactWithFocusedElement: Boolean = {
    viewModel match {
      case Some(viewModel) =>
        editFocusedTask.call(viewModel.interact[RenderType](currentlyRendered, renderOptionDialog))
      case None => false
    }
  }
}
