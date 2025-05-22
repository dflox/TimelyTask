package me.timelytask.controller

import me.timelytask.controller.commands.CommandHandler
import me.timelytask.model.Model
import me.timelytask.util.Publisher

trait Controller(modelPublisher: Publisher[Model],
                 commandHandler: CommandHandler){
  
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