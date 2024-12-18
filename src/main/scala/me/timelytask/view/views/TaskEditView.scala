package me.timelytask.view.views

import me.timelytask.model.Task
import me.timelytask.model.settings.TASKEdit
import me.timelytask.view.events.*
import me.timelytask.view.viewmodel.dialogmodel.{InputDialogModel, OptionDialogModel}
import me.timelytask.view.viewmodel.{TaskEditViewModel, ViewModel}

trait TaskEditView[RenderType] extends View[TASKEdit, TaskEditViewModel, RenderType] {
  val saveTask: SaveTask = SaveTask.createEvent
  val cancelTask: CancelTask = CancelTask.createEvent

  def renderOptionDialog: (optionDialogModel: Option[OptionDialogModel[?]],
                           renderedViewOption: Option[RenderType]) => Option[?]
  
  def renderInputDialog: (inputDialogModel: Option[InputDialogModel[?]],
                          renderedViewInput: Option[RenderType]) => Option[?]
  
  protected def interactWithFocusedElement: Boolean = {
    viewModel match
      case Some(viewModel) =>
        viewModelPublisher.update(viewModel.interact[RenderType](currentlyRendered, 
          renderOptionDialog, renderInputDialog))
        true
      case None => false
  }
}
