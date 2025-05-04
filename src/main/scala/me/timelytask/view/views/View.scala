package me.timelytask.view.views

import me.timelytask.model.Task
import me.timelytask.model.settings.ViewType
import me.timelytask.model.utility.{Key, Space}
import me.timelytask.util.Publisher
import me.timelytask.view.events.{ChangeView, Event, MoveFocus, SetFocusTo}
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.viewmodel.dialogmodel.DialogModel
import me.timelytask.view.viewmodel.elemts.FocusDirection
import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

trait View[VT <: ViewType : ClassTag, ViewModelType <: ViewModel[VT, ViewModelType], RenderType] {
  def init(): Unit = {
    viewModelPublisher.addListener(update)
  }

//  val changeView: ChangeView = ChangeView.createEvent
//  val moveFocus: Event[FocusDirection] = MoveFocus.createEvent[VT]
//  val setFocusTo: Event[Task] = SetFocusTo.createEvent[VT]

  private def renderDialog: (dialogModel: Option[DialogModel[?]]) => Option[?] =
    (dialogModel: Option[DialogModel[?]]) => dialogFactory(dialogModel, currentlyRendered) match
      case Some(dialog: Dialog[?, RenderType]) => dialog()
      case None => None

  def dialogFactory: DialogFactory[RenderType]

  def keymapPublisher: Publisher[Keymap[VT, ViewModelType, View[VT, ViewModelType, ?]]]

  def viewModelPublisher: Publisher[ViewModelType]

  def render: (RenderType, ViewType) => Unit

  protected var currentlyRendered: Option[RenderType] = None

  def getCurrentlyRendered: Option[RenderType] = currentlyRendered

  def viewModel: Option[ViewModelType] = viewModelPublisher.getValue

  def handleKey(key: Option[Key]): Boolean = {
    val (test, keyTested) = testIfFocusedElementIsTriggered(key)
    if test then return true
    Try[Boolean] {
      keymapPublisher.getValue.get.handleKey(keyTested, this)
    } match
      case Success(value) => value
      case Failure(exception) => throw new Exception("Keymap not found")
  }

  def update(viewModel: Option[ViewModelType]): Boolean

  private def testIfFocusedElementIsTriggered(key: Option[Key]): (Boolean, Option[Key]) = {
    if key == Space then
      return (interactWithFocusedElement, None)
    (false, key)
  }

  private def interactWithFocusedElement: Boolean = {
    viewModel match
      case Some(viewModel) =>
        viewModelPublisher.update(viewModel.interact(renderDialog))
        true
      case None => false
  }
}
