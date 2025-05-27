package me.timelytask.controller.commands

import me.timelytask.model.{Priority, State, Tag, Task}

trait InversibleHandler[Args] {
  def apply(args: Args): Boolean

  def unapply(args: Args): Boolean
}

trait InversibleCommand[Args](handler: InversibleHandler[Args], args: Args)
  extends Command[Args] {

  override def execute: Boolean = {
    handler(args)
  }

  override def redo: Boolean = {
    handler(args)
  }

  override def undo: Boolean = {
    handler.unapply(args)
  }
}

trait InversibleCommandCompanion[T <: InversibleCommand[Args], Args] {
  protected var handler: Option[InversibleHandler[Args]] = None

  def setHandler(newHandler: InversibleHandler[Args]): Boolean = {
    handler = Some(newHandler)
    true
  }

  def createCommand(args: Args): T = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    create(args)
  }

  protected def create(args: Args): T
}

case class AddTask(handler: InversibleHandler[Task], args: Task)
  extends InversibleCommand[Task](handler, args)

object AddTask extends InversibleCommandCompanion[AddTask, Task] {
  protected def create(args: Task): AddTask = AddTask(handler.get, args)
}

case class RemoveTask(handler: InversibleHandler[Task], args: Task) extends InversibleCommand[Task](
  handler, args)

object RemoveTask extends InversibleCommandCompanion[RemoveTask, Task] {
  protected def create(using args: Task): RemoveTask = RemoveTask(handler.get, args)
}

case class AddTag(handler: InversibleHandler[Tag], args: Tag) extends InversibleCommand[Tag](
  handler, args)

object AddTag extends InversibleCommandCompanion[AddTag, Tag] {
  protected def create(using args: Tag): AddTag = AddTag(handler.get, args)
}

case class RemoveTag(handler: InversibleHandler[Tag], args: Tag) extends InversibleCommand[Tag](
  handler, args)

object RemoveTag extends InversibleCommandCompanion[RemoveTag, Tag] {
  protected def create(using args: Tag): RemoveTag = RemoveTag(handler.get, args)
}

case class AddPriority(handler: InversibleHandler[Priority], args: Priority)
  extends InversibleCommand[Priority](handler, args)

object AddPriority extends InversibleCommandCompanion[AddPriority, Priority] {
  protected def create(using args: Priority): AddPriority = AddPriority(handler.get, args)
}

case class RemovePriority(handler: InversibleHandler[Priority], args: Priority)
  extends InversibleCommand[Priority](handler, args)

object RemovePriority extends InversibleCommandCompanion[RemovePriority, Priority] {
  protected def create(using args: Priority): RemovePriority = RemovePriority(handler.get, args)
}

case class AddState(handler: InversibleHandler[State], args: State)
  extends InversibleCommand[State](handler, args)

object AddState extends InversibleCommandCompanion[AddState, State] {
  protected def create(using args: State): AddState = AddState(handler.get, args)
}

case class RemoveState(handler: InversibleHandler[State], args: State)
  extends InversibleCommand[State](handler, args)

object RemoveState extends InversibleCommandCompanion[RemoveState, State] {
  protected def create(using args: State): RemoveState = RemoveState(handler.get, args)
}