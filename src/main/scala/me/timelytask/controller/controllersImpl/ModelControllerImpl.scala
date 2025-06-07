package me.timelytask.controller.controllersImpl

import me.timelytask.controller.commands.*
import me.timelytask.controller.ModelController
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher

class ModelControllerImpl(modelPublisher: Publisher[Model], commandHandler: CommandHandler)
  extends Controller(modelPublisher, commandHandler)
  with ModelController {

  def init(): Unit = {
//    EditTask.setHandler(
//      (args: Task) => exchangeTask(args).nonEmpty,
//      new StoreHandler[Task, Task] {
//        override def apply(args: Task): Option[Task] = {
//          exchangeTask(args)
//        }
//      },
//      new RestoreHandler[Task, Task] {
//        override def apply(args: Task, value: Task): Boolean = {
//          exchangeTask(value).nonEmpty
//        }
//      }
//    )
  }

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

  override def addTask(task: Task): Unit = commandHandler.handle(new InversibleCommand[Task](
    new InversibleHandler[Task] {
      override def apply(args: Task): Boolean = addNewTask(args)

      override def unapply(args: Task): Boolean = deleteTask(args)
    },
    task
  ) {})

  private def addNewTask(task: Task): Boolean = {
    if model().isEmpty then {
      false
    } else {
      Some(model().get.copy(tasks = task :: model().get.tasks))
    }
  }

  private def deleteTask(task: Task): Boolean = {
    if model().isEmpty then {
      false
    } else {
      Some(model().get.copy(tasks = model().get.tasks.filterNot(_.uuid.equals(task.uuid))))
    }
  }
  
  override def removeTask(task: Task): Unit = ???

  override def updateTask(task: Task): Unit = ???
}
