package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.controller.commands.{AddTask, InversibleHandler}

class ModelController(using modelPublisher: Publisher[Model]) 
  extends Controller {
  
  AddTask.setHandler(new InversibleHandler[Task] {
    def apply: Task => Boolean = (args: Task) => {
      Some(model().copy(tasks = List[Task](args)))
    }
    def unapply: Task => Boolean = (args: Task) => {
      Some(model().copy(tasks = model().tasks.filterNot(_.uuid.equals(args.uuid))))
    }
  })
}
