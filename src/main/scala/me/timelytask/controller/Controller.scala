// src/main/scala/me/timelytask/controller/Controller.scala
package me.timelytask.controller

import me.timelytask.util.{MultiTypeObserver, Publisher}
import me.timelytask.view.viewmodel.ViewModel

trait Controller(using viewModelPublisher: Publisher[ViewModel]) extends MultiTypeObserver {
  given Conversion[Option[ViewModel], Boolean] with {
    def apply(option: Option[ViewModel]): Boolean = option match {
      case Some(viewModel) =>
        viewModelPublisher.update(viewModel)
        true
      case None => false
    }
  }
}