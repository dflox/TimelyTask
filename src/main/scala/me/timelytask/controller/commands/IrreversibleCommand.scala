package me.timelytask.controller.commands

trait IrreversibleCommand[Args](handler: Handler[Args], args: Args) extends Command[Args] {
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
