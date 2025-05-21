package me.timelytask.controller

import me.timelytask.model.Model
import me.timelytask.util.Publisher

trait Controller(modelPublisher: Publisher[Model]){
  
  val model: () => Option[Model] = () => {
    modelPublisher.getValue
  }
  
  def init(): Unit

  given Conversion[Option[Model], Boolean] with {
    def apply(option: Option[Model]): Boolean = {
      modelPublisher.update(option)
      option.isDefined
    }
  }
}