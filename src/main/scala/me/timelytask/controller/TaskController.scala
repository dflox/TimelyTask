package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import me.timelytask.controller.CalendarController.observe
import me.timelytask.model.modelPublisher
import me.timelytask.model.settings.*
import me.timelytask.model.settings.activeViewPublisher
import me.timelytask.view.viewmodel.{ViewModel, viewModelPublisher}
import me.timelytask.view.viewmodel.TaskModel

object TaskController extends Controller {
  val viewModel: () => TaskModel = () => viewModelPublisher.getValue
    .asInstanceOf[TaskModel]

  given Conversion[Option[ViewModel], Boolean] with {
    def apply(option: Option[ViewModel]): Boolean = option match {
      case Some(_) =>
        viewModelPublisher.update(option.get)
        true
      case None => false
    }
  }

  observe(activeViewPublisher) { viewType =>
    if (viewType == ViewType.TASK) {
      viewModelPublisher.update(TaskModel(viewModel().model))
    } else {
      None
    }
  }

  observe(modelPublisher) { model =>
    if (activeViewPublisher.getValue == ViewType.TASK) {
      viewModelPublisher.update(TaskModel( model))
    } else {
      None
    }
  }
}
