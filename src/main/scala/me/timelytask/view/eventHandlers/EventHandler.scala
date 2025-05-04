package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.{Command, CommandHandler}
import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.ViewModel

import java.util.concurrent.LinkedBlockingQueue

trait EventHandler[T <: ViewType, M <: ViewModel[T, M]](viewModelPublisher: Publisher[M],
                                                        modelPublisher: Publisher[Model],
                                                        undoManager: CommandHandler,
                                                        activeViewPublisher: Publisher[ViewType],
                                                        commandQueue: LinkedBlockingQueue[Command[?]]) {

  def init(): Unit
  
  val model: () => Option[Model] = () => modelPublisher.getValue
  
  given Conversion[Option[M], Boolean] with {
    def apply(option: Option[M]): Boolean = {
      viewModelPublisher.update(option)
      option.isDefined
    }
  }
}
