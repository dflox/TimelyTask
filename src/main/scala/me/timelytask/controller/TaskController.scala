package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import me.timelytask.controller.CalendarController.observe
import me.timelytask.model.{Task, modelPublisher}
import me.timelytask.model.settings.*
import me.timelytask.model.settings.activeViewPublisher
import me.timelytask.view.viewmodel.{ViewModel, viewModelPublisher}
import me.timelytask.view.viewmodel.TaskModel

object TaskController extends Controller {
  val viewModel: () => TaskModel = () => viewModelPublisher.getValue
    .asInstanceOf[TaskModel]

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
  
  AddTask.setHandler(() => {
    activeViewPublisher.update(ViewType.TASK)
    Some(new TaskModel(viewModel().model.copy(tasks = List[Task](Task.emptyTask))))
  })
}
