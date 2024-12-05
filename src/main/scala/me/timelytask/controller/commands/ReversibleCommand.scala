package me.timelytask.controller.commands

import me.timelytask.model.*
import me.timelytask.util.Publisher

trait StoreHandler[Args, StateValue] {
  def apply(args: Args): StateValue
}

trait RestoreHandler[Args, StateValue] {
  def apply(args: Args, value: StateValue): Boolean
}

trait ReversibleCommand[Args, StateValue](using handler: Handler[Args], args: Args,
                                          storeHandler: StoreHandler[Args, StateValue],
                                          restoreHandler: RestoreHandler[Args, StateValue])
  extends Command[Args] {
  private var state: Option[StateValue] = None

  override def doStep(): Boolean = {
    state match {
      case Some(s) => false
      case None =>
        state = Some(storeHandler(args))
        handler(args)
    }
  }

  override def redo: Boolean = {
    state match {
      case Some(s) => handler(args)
      case None => false
    }
  }

  override def undoStep: Boolean = {
    state match {
      case Some(s) => restoreHandler(args, s)
      case None => false
    }
  }
}

trait ReversibleCommandCompanion[StateValue, T <: ReversibleCommand[Args, StateValue], Args]{
  private var handler: Option[Handler[Args]] = None
  private var storeHandler: Option[StoreHandler[Args, StateValue]] = None
  private var restoreHandler: Option[RestoreHandler[Args, StateValue]] = None

  def setHandler(newHandler: Handler[Args], newStoreHandler: StoreHandler[Args, StateValue],
                 newRestoreHandler: RestoreHandler[Args, StateValue]): Boolean = {
    handler = Some(newHandler)
    storeHandler = Some(newStoreHandler)
    restoreHandler = Some(newRestoreHandler)
  }

  def createCommand(args: Args): T = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    create(args)
  }

  protected def create(args: Args): T
}

case class ChangeDataFilePath()(using handler: Handler[String], args: String,
                                storeHandler: StoreHandler[String, String],
                                restoreHandler: RestoreHandler[String, String])
  extends ReversibleCommand[String, String]
object ChangeDataFilePath extends ReversibleCommandCompanion[String, ChangeDataFilePath, String]{
  protected def create(using handler: Handler[String], args: String,
                       storeHandler: StoreHandler[String, String],
                       restoreHandler: RestoreHandler[String, String]): ChangeDataFilePath = ChangeDataFilePath()
}

case class EditTask()(using handler: Handler[Task], args: Task,
                      storeHandler: StoreHandler[Task, Task], 
                      restoreHandler: RestoreHandler[Task, Task]) 
  extends ReversibleCommand[Task, Task]
object EditTask extends ReversibleCommandCompanion[Task, EditTask, Task]{
  protected def create(using handler: Handler[Task], args: Task,
                       storeHandler: StoreHandler[Task, Task],
                       restoreHandler: RestoreHandler[Task, Task]): EditTask = EditTask()
}

case class EditTag()(using handler: Handler[Tag], args: Tag,
                     storeHandler: StoreHandler[Tag, Tag],
                     restoreHandler: RestoreHandler[Tag, Tag])
  extends ReversibleCommand[Tag, Tag]
object EditTag extends ReversibleCommandCompanion[Tag, EditTag, Tag]{
  protected def create(using handler: Handler[Tag], args: Tag,
                       storeHandler: StoreHandler[Tag, Tag],
                       restoreHandler: RestoreHandler[Tag, Tag]): EditTag = EditTag()
}

case class EditPriority()(using handler: Handler[Priority], args: Priority,
                          storeHandler: StoreHandler[Priority, Priority],
                          restoreHandler: RestoreHandler[Priority, Priority])
  extends ReversibleCommand[Priority, Priority]
object EditPriority extends ReversibleCommandCompanion[Priority, EditPriority, Priority]{
  protected def create(using handler: Handler[Priority], args: Priority,
                       storeHandler: StoreHandler[Priority, Priority],
                       restoreHandler: RestoreHandler[Priority, Priority]): EditPriority = EditPriority()
}

case class EditState()(using handler: Handler[State], args: State,
                       storeHandler: StoreHandler[State, State],
                        restoreHandler: RestoreHandler[State, State])
  extends ReversibleCommand[State, State] 
object EditState extends ReversibleCommandCompanion[State, EditState, State]{
  protected def create(using handler: Handler[State], args: State,
                       storeHandler: StoreHandler[State, State],
                       restoreHandler: RestoreHandler[State, State]): EditState = EditState()
}