package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.controller.commands.AddTask

class ModelController(using modelPublisher: Publisher[Model]) 
  extends Controller {
  
  AddTask.setHandler(() => {
    model.copy(tasks = List[Task](Task.emptyTask))
  })
}
