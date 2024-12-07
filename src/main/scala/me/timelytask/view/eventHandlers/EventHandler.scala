package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.UndoManager
import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.ViewModel

trait EventHandler[T <: ViewType, M <: ViewModel[T]](using
                                                     viewModelPublisher: Publisher[M],
                                                     modelPublisher: Publisher[Model],
                                                     undoManager: UndoManager,
                                                     activeViewPublisher: Publisher[ViewType]) {

  val model: () => Model = () => modelPublisher.getValue

  given Conversion[Option[M], Boolean] with {
    def apply(option: Option[M]): Boolean = option match {
      case Some(viewModel) =>
        viewModelPublisher.update(viewModel)
        true
      case None => false
    }
  }
}
