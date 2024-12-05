package me.timelytask.controller.commands

import me.timelytask.model.{Priority, State, Tag, Task}

trait InversibleCommand[Args](using handler: Handler[Args], args: Args,
                              undoHandler: Handler[Args])
  extends Command[Args] {

  override def redo: Boolean = {
    handler(args)
  }

  override def undoStep: Boolean = {
    undoHandler(args)
  }
}

trait InversibleCommandCompanion[T <: InversibleCommand[Args], Args] {
  private var handler: Option[Handler[Args]] = None
  private var undoHandler: Option[Handler[Args]] = None

  def setHandler(newHandler: Handler[Args], newUndoHandler: Handler[Args]): Boolean = {
    handler = Some(newHandler)
    undoHandler = Some(newUndoHandler)
    true
  }

  def createCommand(args: Args): T = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    create(args)
  }

  protected def create(args: Args): T
}

case class AddTask()(using handler: Handler[Task], args: Task) extends InversibleCommand[Task]
object AddTask extends InversibleCommandCompanion[AddTask, Task] {
  protected def create(using args: Task): AddTask = AddTask()
}

case class RemoveTask()(using handler: Handler[Task], args: Task) extends InversibleCommand[Task]
object RemoveTask extends InversibleCommandCompanion[RemoveTask, Task] {
  protected def create(using args: Task): RemoveTask = RemoveTask()
}

case class AddTag()(using handler: Handler[Tag], args: Tag) extends InversibleCommand[Tag]
object AddTag extends InversibleCommandCompanion[AddTag, Tag] {
  protected def create(using args: Tag): AddTag = AddTag()
}

case class RemoveTag()(using handler: Handler[Tag], args: Tag) extends InversibleCommand[Tag]
object RemoveTag extends InversibleCommandCompanion[RemoveTag, Tag] {
  protected def create(using args: Tag): RemoveTag = RemoveTag()
}

case class AddPriority()(using handler: Handler[Priority], args: Priority) extends InversibleCommand[Priority]
object AddPriority extends InversibleCommandCompanion[AddPriority, Priority] {
  protected def create(using args: Priority): AddPriority = AddPriority()
}

case class RemovePriority()(using handler: Handler[Priority], args: Priority) extends InversibleCommand[Priority]
object RemovePriority extends InversibleCommandCompanion[RemovePriority, Priority] {
  protected def create(using args: Priority): RemovePriority = RemovePriority()
}

case class AddState()(using handler: Handler[State], args: State) extends InversibleCommand[State]
object AddState extends InversibleCommandCompanion[AddState, State] {
  protected def create(using args: State): AddState = AddState()
}

case class RemoveState()(using handler: Handler[State], args: State) extends InversibleCommand[State]
object RemoveState extends InversibleCommandCompanion[RemoveState, State] {
  protected def create(using args: State): RemoveState = RemoveState()
}