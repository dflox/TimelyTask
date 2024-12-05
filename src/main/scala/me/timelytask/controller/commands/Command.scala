package me.timelytask.controller.commands

import me.timelytask.model.Model
import me.timelytask.util.Publisher

trait Handler[Args] {
  def apply(args: Args): Boolean
}

trait Command[Args](using handler: Handler[Args], args: Args) {
  def doStep(): Boolean = {
    handler(args)
  }

  def redo: Boolean

  def undoStep: Boolean
}



trait CommandCompanion[T <: Command[Args], Args] {
  private var handler: Option[Handler[Args]] = None

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

case class StartApp()(using handler: Handler[Unit], args: Unit) extends Command[Unit] {
  def redo: Boolean = false
  def undoStep: Boolean = false
}
object StartApp extends CommandCompanion[StartApp, Unit] {
  protected def create(using args: Unit, handler: Handler[Unit]): StartApp = StartApp()
}

case class SaveData()(using handler: Handler[Unit], args: Unit) extends Command[Unit]{
  def redo: Boolean = false
  def undoStep: Boolean = false
}
object SaveData extends CommandCompanion[SaveData, Unit]{
  protected def create(using args: Unit, handler: Handler[Unit]): SaveData = SaveData()
}

case class LoadData()(using handler: Handler[Unit], args: Unit) extends Command[Unit]{
  def redo: Boolean = false
  def undoStep: Boolean = false
}
object LoadData extends CommandCompanion[LoadData, Unit]{
  protected def create(using args: Unit, handler: Handler[Unit]): LoadData = LoadData()
}