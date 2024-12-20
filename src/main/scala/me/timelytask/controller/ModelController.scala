package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import me.timelytask.controller.commands.*
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher

class ModelController(using modelPublisher: Publisher[Model])
  extends Controller {

  AddTask.setHandler(new InversibleHandler[Task] {
    override def apply(args: Task): Boolean = {
      if model().isEmpty then {
        false
      } else {
        Some(model().get.copy(tasks = args :: model().get.tasks))
      }
    }

    override def unapply(args: Task): Boolean = {
      if model().isEmpty then {
        false
      } else {
        Some(model().get.copy(tasks = model().get.tasks.filterNot(_.uuid.equals(args.uuid))))
      }
    }
  })

  EditTask.setHandler(
    (args: Task) => exchangeTask(args).nonEmpty,
    new StoreHandler[Task, Task] {
      override def apply(args: Task): Option[Task] = {
        exchangeTask(args)
      }
    },
    new RestoreHandler[Task, Task] {
      override def apply(args: Task, value: Task): Boolean = {
        exchangeTask(value).nonEmpty
      }
    }
  )

  private def exchangeTask(task: Task): Option[Task] = {
    if model().isEmpty | task.isValid.isDefined then {
      None
    } else {
      val returnTask = model().get.tasks.find(_.uuid.equals(task.uuid)) match {
        case Some(_) => Some(task)
        case None => None
      }
      if returnTask.isDefined & Some(model().get.copy(tasks = model().get.tasks.map(
        t => if t.uuid.equals(task.uuid) then task else t))) then returnTask
      else None
    }
  }
}
