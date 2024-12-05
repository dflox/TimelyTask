package me.timelytask.controller.commands

trait Handler[Args] {
  def apply(args: Args): Boolean
}

trait Command[Args] {
  private var handler: Option[Handler[Args]] = None

  def setHandler(newHandler: Handler[Args]): Unit = {
    if (handler.isDefined) throw new Exception("Handler already set")
    handler = Some(newHandler)
  }

  def doStep(): Boolean = {
    handler match {
      case Some(h) => h()
      case None => false
    }
  }

  def undoStep: Boolean
}

trait CommandCompanion[T <: Command[Args], Args] {
  private var handler: Option[Handler[Args]] = None

  def setHandler(newHandler: Handler[Args]): Unit = {
    handler = Some(newHandler)
  }

  def createCommand(args: Args): T = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    val cmd = create(args)
    cmd.setHandler(handler.get)
    cmd
  }

  protected def create(args: Args): T = {
    val constructor = getClass.getDeclaringClass.getDeclaredConstructor()
    constructor.newInstance(args).asInstanceOf[T]
  }
}


case class StartApp() extends Command[Unit]
object StartApp extends CommandCompanion[StartApp, Unit] {
  def create(): StartApp = StartApp()
}

case class SaveFile() extends Command[String] // String represents the file path
object SaveFile extends CommandCompanion[SaveFile, String]

case class SaveData() extends Command[Unit]
object SaveData extends CommandCompanion[SaveData, Unit]

case class LoadData() extends Command[Unit]
object LoadData extends CommandCompanion[LoadData, Unit]

case class ChangeDataFileType() extends Command[Unit]
object ChangeDataFileType extends CommandCompanion[ChangeDataFileType, Unit]

case class ChangeDataFilePath() extends Command[Unit]
object ChangeDataFilePath extends CommandCompanion[ChangeDataFilePath, Unit]

case class AddTask() extends Command[Unit]
object AddTask extends CommandCompanion[AddTask, Unit]

case class RemoveTask() extends Command[Unit]
object RemoveTask extends CommandCompanion[RemoveTask, Unit]

case class EditTask() extends Command[Unit]
object EditTask extends CommandCompanion[EditTask, Unit]

case class AddTag() extends Command[Unit]
object AddTag extends CommandCompanion[AddTag, Unit]

case class RemoveTag() extends Command[Unit]
object RemoveTag extends CommandCompanion[RemoveTag, Unit]

case class EditTag() extends Command[Unit]
object EditTag extends CommandCompanion[EditTag, Unit]

case class EditPriority() extends Command[Unit]
object EditPriority extends CommandCompanion[EditPriority, Unit]

case class EditState() extends Command[Unit]
object EditState extends CommandCompanion[EditState, Unit]

case class AddPriority() extends Command[Unit]
object AddPriority extends CommandCompanion[AddPriority, Unit]

case class RemovePriority() extends Command[Unit]
object RemovePriority extends CommandCompanion[RemovePriority, Unit]

case class AddState() extends Command[Unit]
object AddState extends CommandCompanion[AddState, Unit]

case class RemoveState() extends Command[Unit]
object RemoveState extends CommandCompanion[RemoveState, Unit]