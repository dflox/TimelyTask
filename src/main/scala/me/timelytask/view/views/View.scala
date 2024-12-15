package me.timelytask.view.views

import me.timelytask.model.Task
import me.timelytask.model.settings.ViewType
import me.timelytask.model.utility.{Key, Space}
import me.timelytask.util.Publisher
import me.timelytask.view.events.{ChangeView, Event, MoveFocus, SetFocusTo}
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.viewmodel.dialogmodel.{ConfirmDialogModel, InputDialogModel, OptionDialogModel}
import me.timelytask.view.viewmodel.elemts.FocusDirection
import scala.util.{Try, Success, Failure}

trait View[VT <: ViewType, ViewModelType <: ViewModel[VT], RenderType] {

  val changeView: ChangeView = ChangeView.createEvent
  val moveFocus: Event[FocusDirection] = MoveFocus.createEvent[VT]
  val setFocusTo: Event[Task] = SetFocusTo.createEvent[VT]

  def keymapPublisher: Publisher[Keymap[VT, ViewModelType, View[VT, ViewModelType, ?]]]
  
  def viewModelPublisher: Publisher[ViewModelType]
  
  def render: (RenderType, ViewType) => Unit
  
  protected var currentlyRendered: Option[RenderType]
  def getCurrentlyRendered: Option[RenderType] = currentlyRendered
  
  def viewModel: Option[ViewModelType] = viewModelPublisher.getValue

  def handleKey(key: Key): Boolean = {
    Try[Boolean] {
      keymapPublisher.getValue.get.handleKey(key, this)
    } match
      case Success(value) => value
      case Failure(exception) => throw new Exception("Keymap not found")
  }

  def update(viewModel: Option[ViewModelType]): Boolean

  viewModelPublisher.addListener(update)
}
