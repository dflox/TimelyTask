package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.{Command, CommandHandler}
import me.timelytask.core.CoreModule
import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.viewmodel.ViewModel

import java.util.concurrent.LinkedBlockingQueue

trait EventContainer[T <: ViewType, M <: ViewModel[T, M]](viewModelPublisher: Publisher[M],
                                                          activeViewPublisher: Publisher[ViewType],
                                                          coreModule: CoreModule) {

  def init(): Unit = {
    coreModule.registerModelUpdater(updateModel)
  }
  
  
  protected def updateModel(model: Option[Model]): Boolean

  val viewModel: () => Option[M] = () => viewModelPublisher.getValue

  val model: () => Option[Model] = () => viewModelPublisher.getValue match {
    case Some(value) => Some(value.model)
    case None => None
  }
  
  given Conversion[Option[M], Boolean] with {
    def apply(option: Option[M]): Boolean = {
      viewModelPublisher.update(option)
      option.isDefined
    }
  }
}
