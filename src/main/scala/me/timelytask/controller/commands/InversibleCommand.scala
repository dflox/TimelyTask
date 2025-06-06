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

// TODO: Rename to something like relative state manipulating command
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