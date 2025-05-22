package me.timelytask.view.views

import com.softwaremill.macwire.wireWith
import me.timelytask.model.settings.{KeymapConfig, ViewType}
import me.timelytask.model.utility.{Key, Space}
import me.timelytask.view.keymaps.{Keymap, KeymapImpl}
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.viewmodel.dialogmodel.DialogModel

import scala.util.{Failure, Success, Try}

trait View[VT <: ViewType, ViewModelType <: ViewModel[VT, ViewModelType], RenderType]
(protected val viewTypeCommonsModule: ViewTypeCommonsModule[VT, ViewModelType]) {
  import viewTypeCommonsModule.*
  
  def init(): Unit = {
    registerKeymapUpdater(keymapUpdateListener())
    viewModelPublisher.addListener(update)
  }
  
  private def renderDialog: (dialogModel: Option[DialogModel[?]]) => Option[?] =
    (dialogModel: Option[DialogModel[?]]) => dialogFactory(dialogModel, currentlyRendered) match
      case Some(dialog: Dialog[?, RenderType]) => dialog()
      case None => None

  private def keymapUpdateListener(): KeymapConfig => Unit = km => keymap = {
    Some(wireWith[KeymapImpl[VT, ViewModelType]](() => KeymapImpl[VT, ViewModelType](km,
      eventResolver)))
  }
  
  def dialogFactory: DialogFactory[RenderType]

  private var keymap: Option[Keymap[VT, ViewModelType]] = None 
  
  def render: (RenderType, ViewType) => Unit

  protected var currentlyRendered: Option[RenderType] = None
  
  private def viewModel: Option[ViewModelType] = viewModelPublisher.getValue

  def handleKey(key: Option[Key]): Boolean = {
    val (test, keyTested) = testIfFocusedElementIsTriggered(key)
    if test then return true
    Try[Boolean] {
      keymap.get.handleKey(keyTested)
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
