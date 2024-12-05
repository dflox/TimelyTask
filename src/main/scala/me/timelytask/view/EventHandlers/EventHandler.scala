package me.timelytask.view.EventHandlers

import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.ViewModel

trait EventHandler[T <: ViewModel](using viewModelPublisher: Publisher[T]) {
  given Conversion[Option[T], Boolean] with {
    def apply(option: Option[T]): Boolean = option match {
      case Some(viewModel) =>
        viewModelPublisher.update(viewModel)
        true
      case None => false
    }
  } 
}
