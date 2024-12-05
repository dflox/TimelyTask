package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.{Model, Task}
import me.timelytask.model.settings.*
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.viewmodel.TaskModel

class TaskController(using viewModelPublisher: Publisher[ViewModel], 
                     activeViewPublisher: Publisher[ViewType],
                     modelPublisher: Publisher[Model]) 
  extends Controller {
  val viewModel: () => TaskModel = () => {
    viewModelPublisher.getValue match {
      case vm: TaskModel => vm
      case vm => TaskModel(vm.model)
    }
  }

  observe(activeViewPublisher) ({ viewType =>
    if (viewType == ViewType.TASK) {
      viewModelPublisher.update(TaskModel(viewModel().model))
    } else {
      None
    }
  }, Some(this))

  observe(modelPublisher) ({ model =>
    if (activeViewPublisher.getValue == ViewType.TASK) {
      viewModelPublisher.update(TaskModel(model))
    } else {
      None
    }
  }, Some(this))
  
  AddTask.setHandler(() => {
    activeViewPublisher.update(ViewType.TASK)
    Some(TaskModel(viewModel().model.copy(tasks = List[Task](Task.emptyTask))))
  })
}
