package me.timelytask.controller.commands

@FunctionalInterface
trait Handler[Args] {
  def apply(args: Args): Boolean
}

trait Command[Args] {
  def execute: Boolean

  def redo: Boolean

  def undo: Boolean
}

trait UndoableCommand[Args](handler: Handler[Args], args: Args) extends Command[Args] {
  private var done: Boolean = false

  override def execute: Boolean = {
    if (!done) {
      done = handler.apply(args)
      done
    } else false
  }

  override def undo: Boolean = {
    false
  }

  override def redo: Boolean = execute
}

// TODO: braucht man die CommandCompanions überhaupt?
trait CommandCompanion[T <: Command[Args], Args] {
  protected var handler: Option[Handler[Args]] = None

  def setHandler(newHandler: Handler[Args]): Boolean = {
    handler = Some(newHandler)
    true
  }

  def createCommand(args: Args): T = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    create(args)
  }

  protected def create(args: Args): T
}