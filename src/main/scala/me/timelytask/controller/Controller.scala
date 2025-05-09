package me.timelytask.controller

import me.timelytask.model.Model
import me.timelytask.util.publisher.PublisherImpl

trait Controller(modelPublisher: PublisherImpl[Model]){
  
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