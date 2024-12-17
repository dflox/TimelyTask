// src/main/scala/me/timelytask/controller/Controller.scala
package me.timelytask.controller

import me.timelytask.model.Model
import me.timelytask.util.{MultiTypeObserver, Publisher}
import me.timelytask.view.viewmodel.ViewModel

trait Controller(using modelPublisher: Publisher[Model]) extends MultiTypeObserver {
  val model: () => Option[Model] = () => {
    modelPublisher.getValue
  }

  given Conversion[Option[Model], Boolean] with {
    def apply(option: Option[Model]): Boolean = {
      modelPublisher.update(option)
      option.isDefined
    }
  }
}