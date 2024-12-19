package me.timelytask.controller.commands

import me.timelytask.model.Model
import me.timelytask.util.Publisher

trait Handler[Args] {
  def apply(args: Args): Boolean
}

trait Command[Args] {
  def doStep(): Boolean

  def redo: Boolean

  def undoStep: Boolean
}

trait UndoableCommand[Args](handler: Handler[Args], args: Args) extends Command[Args] {
  private var done: Boolean = false

  override def doStep(): Boolean = {
    if (!done) {
      done = handler.apply(args)
      done
    } else false
  }

  override def undoStep: Boolean = {
    false
  }
  
  override def redo: Boolean = doStep()
}


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

case class StartApp(handler: Handler[Unit], args: Unit) extends UndoableCommand[Unit](handler, args)
object StartApp extends CommandCompanion[StartApp, Unit] {
  protected def create(args: Unit): StartApp = StartApp(handler.get, args)
}

case class SaveData(handler: Handler[Unit], args: Unit) extends UndoableCommand[Unit](handler, args)
object SaveData extends CommandCompanion[SaveData, Unit]{
  protected def create(args: Unit): SaveData = SaveData(handler.get, args)
}

case class LoadData(handler: Handler[Unit], args: Unit) extends UndoableCommand[Unit](handler, args)
object LoadData extends CommandCompanion[LoadData, Unit]{
  protected def create(args: Unit): LoadData = LoadData(handler.get, args)
}

case class Exit(handler: Handler[Unit], args: Unit) extends UndoableCommand[Unit](handler, args)
object Exit extends CommandCompanion[Exit, Unit]{
  protected def create(args: Unit): Exit = Exit(handler.get, args)
}

case class SaveAndExit(handler: Handler[Unit], args: Unit) extends UndoableCommand[Unit](handler, args)
object SaveAndExit extends CommandCompanion[SaveAndExit, Unit]{
  protected def create(args: Unit): SaveAndExit = SaveAndExit(handler.get, args)
}
