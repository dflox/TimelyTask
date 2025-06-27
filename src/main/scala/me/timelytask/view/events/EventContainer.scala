package me.timelytask.view.events

import me.timelytask.core.CoreModule
import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.ViewModel

trait EventContainer[T <: ViewType, M <: ViewModel[T, M]](viewModelPublisher: Publisher[M],
                                                          activeViewPublisher: Publisher[ViewType],
                                                          eventHandler: EventHandler,
                                                          coreModule: CoreModule,
                                                          val userToken: String) {

  def init(): Unit = {
    coreModule.registerModelListener(updateModel, userToken)
  }
  
  protected def updateModel(model: Option[Model]): Boolean

  protected val viewModel: () => Option[M] = () => viewModelPublisher.getValue

  protected val model: () => Option[Model] = () => viewModelPublisher.getValue match {
    case Some(value) => Some(value.model)
    case None => None
  }
  
  protected given Conversion[Option[M], Boolean] with {
    def apply(option: Option[M]): Boolean = {
      viewModelPublisher.update(option)
      option.isDefined
    }
  }
}
