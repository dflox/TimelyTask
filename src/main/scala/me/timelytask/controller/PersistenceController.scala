package me.timelytask.controller

import me.timelytask.TimelyTask
import me.timelytask.model.Model
import me.timelytask.model.settings.{Action, Exit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.ViewModel

class PersistenceController(viewModelPublisher: Publisher[ViewModel],
                            modelPublisher: Publisher[Model],
                            activeViewPublisher: Publisher[ViewType])
  extends Controller {
  override def handleAction(action: Action): Option[ViewModel] = {
    action match {
      case Exit =>
        //     TODO:   modelPublisher.getCurrentModel()
        //        activeViewPublisher.updateActiveView(ViewType.EXIT)
        //        viewModelPublisher.getCurrentViewModel
        TimelyTask.exit()
        None
      case _ => Some(viewModelPublisher.getValue)
    }
  }

  override def onChange(newModel: Model): Unit = {
    // TODO: save model
  }

}
