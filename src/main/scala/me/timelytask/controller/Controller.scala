package me.timelytask.controller

import me.timelytask.util.MultiTypeObserver
import me.timelytask.view.viewmodel.{ViewModel, viewModelPublisher}

trait Controller extends MultiTypeObserver {
  given Conversion[Option[ViewModel], Boolean] with {
    def apply(option: Option[ViewModel]): Boolean = option match {
      case Some(_) =>
        viewModelPublisher.update(option.get)
        true
      case None => false
    }
  }
}
