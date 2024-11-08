package me.timelytask.controller

import me.timelytask.TimelyTask
import me.timelytask.model.Model
import me.timelytask.model.settings.{Action, Exit}
import me.timelytask.view.viewmodel.ViewModel

class PersistenceController(viewModelPublisher: ViewModelPublisher,
                            modelPublisher: ModelPublisher,
                            activeViewPublisher: ActiveViewPublisher) 
  extends Controller {
  override def handleAction(action: Action): Option[ViewModel] = {
    action match {
      case Exit =>
        //     TODO:   modelPublisher.getCurrentModel()
        //        activeViewPublisher.updateActiveView(ViewType.EXIT)
        //        viewModelPublisher.getCurrentViewModel
        TimelyTask.exit()
        None
      case _ => Some(viewModelPublisher.getCurrentViewModel)
    }
  }

  override def onModelChange(newModel: Model): Unit = {
    // TODO: save model
  }

}
