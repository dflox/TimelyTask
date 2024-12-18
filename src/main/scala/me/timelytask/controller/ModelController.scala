package me.timelytask.controller

import com.github.nscala_time.time.Imports.*
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.controller.commands.{AddTask, EditTask, InversibleHandler, RestoreHandler, StoreHandler}

class ModelController(using modelPublisher: Publisher[Model]) 
  extends Controller {
  
  AddTask.setHandler(new InversibleHandler[Task] {
    def apply: Task => Boolean = (args: Task) => {
      if model().isEmpty then {
        false
      } else {
        Some(model().get.copy(tasks = args :: model().get.tasks))
      }
    }
    def unapply: Task => Boolean = (args: Task) => {
      if model().isEmpty then {
        false
      }else {
        Some(model().get.copy(tasks = model().get.tasks.filterNot(_.uuid.equals(args.uuid))))
      } 
    }
  })
  
  EditTask.setHandler((args: Task) => {
    if model().isEmpty | args.isValid.isDefined then {
      false
    } else {
      Some(model().get.copy(tasks = model().get.tasks.map(task => if task.uuid.equals(args.uuid) then args else task)))
    }},
    new StoreHandler[Task, Task] {
      def apply: Task => Boolean = (args: Task) => {
        exchangeTask(args)
      }
    },
    new RestoreHandler[Task, Task] {
      def apply: Task => Boolean = (args: Task) => {
        exchangeTask(args)
      }
    }
  )
  
  private def exchangeTask(task: Task): Boolean = {
    if model().isEmpty | task.isValid.isDefined then {
      false
    } else {
      Some(model().get.copy(tasks = model().get.tasks.map(t => if t.uuid.equals(task.uuid) then task else t)))
    }
  }
}
