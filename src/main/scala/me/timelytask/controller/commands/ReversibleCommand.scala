package me.timelytask.controller.commands

import me.timelytask.model.*
import me.timelytask.util.publisher.PublisherImpl

trait StoreHandler[Args, StateValue] {
  def apply(args: Args): Option[StateValue]
}

trait RestoreHandler[Args, StateValue] {
  def apply(args: Args, value: StateValue): Boolean
}

trait ReversibleCommand[Args, StateValue](handler: Handler[Args], args: Args,
                                          storeHandler: StoreHandler[Args, StateValue],
                                          restoreHandler: RestoreHandler[Args, StateValue])
  extends Command[Args] {
  private var state: Option[StateValue] = None

  override def execute: Boolean = {
    state match {
      case Some(s) => false
      case None =>
        state = storeHandler(args)
        handler(args)
    }
  }

  override def redo: Boolean = {
    state match {
      case Some(s) => handler(args)
      case None => false
    }
  }

  override def undo: Boolean = {
    state match {
      case Some(s) => restoreHandler(args, s)
      case None => false
    }
  }
}

trait ReversibleCommandCompanion[StateValue, T <: ReversibleCommand[Args, StateValue], Args] {
  protected var handler: Option[Handler[Args]] = None
  protected var storeHandler: Option[StoreHandler[Args, StateValue]] = None
  protected var restoreHandler: Option[RestoreHandler[Args, StateValue]] = None

  def setHandler(newHandler: Handler[Args], newStoreHandler: StoreHandler[Args, StateValue],
                 newRestoreHandler: RestoreHandler[Args, StateValue]): Boolean = {
    handler = Some(newHandler)
    storeHandler = Some(newStoreHandler)
    restoreHandler = Some(newRestoreHandler)
    true
  }

  def createCommand(args: Args): T = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    create(args)
  }

  protected def create(args: Args): T
}

case class ChangeDataFilePath(handler: Handler[String], args: String,
                              storeHandler: StoreHandler[String, String],
                              restoreHandler: RestoreHandler[String, String])
  extends ReversibleCommand[String, String](handler, args, storeHandler, restoreHandler)

object ChangeDataFilePath extends ReversibleCommandCompanion[String, ChangeDataFilePath, String] {
  protected def create(args: String): ChangeDataFilePath = ChangeDataFilePath(handler.get, args,
    storeHandler.get, restoreHandler.get)
}

case class EditTask(handler: Handler[Task], args: Task,
                    storeHandler: StoreHandler[Task, Task],
                    restoreHandler: RestoreHandler[Task, Task])
  extends ReversibleCommand[Task, Task](handler, args, storeHandler, restoreHandler)

object EditTask extends ReversibleCommandCompanion[Task, EditTask, Task] {
  protected def create(args: Task): EditTask = EditTask(handler.get, args, storeHandler.get,
    restoreHandler.get)
}

case class EditTag(handler: Handler[Tag], args: Tag,
                   storeHandler: StoreHandler[Tag, Tag],
                   restoreHandler: RestoreHandler[Tag, Tag])
  extends ReversibleCommand[Tag, Tag](handler, args, storeHandler, restoreHandler)

object EditTag extends ReversibleCommandCompanion[Tag, EditTag, Tag] {
  protected def create(args: Tag): EditTag = EditTag(handler.get, args, storeHandler.get,
    restoreHandler.get)
}

case class EditPriority(handler: Handler[Priority], args: Priority,
                        storeHandler: StoreHandler[Priority, Priority],
                        restoreHandler: RestoreHandler[Priority, Priority])
  extends ReversibleCommand[Priority, Priority](handler, args, storeHandler, restoreHandler)

object EditPriority extends ReversibleCommandCompanion[Priority, EditPriority, Priority] {
  protected def create(args: Priority): EditPriority = EditPriority(handler.get, args,
    storeHandler.get,
    restoreHandler.get)
}

case class EditState(handler: Handler[State], args: State,
                     storeHandler: StoreHandler[State, State],
                     restoreHandler: RestoreHandler[State, State])
  extends ReversibleCommand[State, State](handler, args, storeHandler, restoreHandler)

object EditState extends ReversibleCommandCompanion[State, EditState, State] {
  protected def create(args: State): EditState = EditState(handler.get, args, storeHandler.get,
    restoreHandler.get)
}