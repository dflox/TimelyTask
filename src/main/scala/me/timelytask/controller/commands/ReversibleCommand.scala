package me.timelytask.controller.commands

trait ReversibleHandler[Args] {
  def apply(args: Args): Boolean

  def unapply(args: Args): Boolean
}

trait ReversibleCommand[Args](handler: ReversibleHandler[Args], args: Args)
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

trait ReversibleCommandCompanion[T <: ReversibleCommand[Args], Args] {
  protected var handler: Option[ReversibleHandler[Args]] = None

  def setHandler(newHandler: ReversibleHandler[Args]): Boolean = {
    handler = Some(newHandler)
    true
  }

  def createCommand(args: Args): T = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    create(args)
  }

  protected def create(args: Args): T
}