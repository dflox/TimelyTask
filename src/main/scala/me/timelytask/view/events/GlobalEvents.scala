package me.timelytask.view.events

import me.timelytask.model.settings.ViewType

import java.util.UUID

//App Events
case class Undo(handler: Handler[Unit],
                argumentProvider: ArgumentProvider[Unit],
                isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object Undo extends EventCompanion[Undo, Unit] {
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): Undo = Undo(handler.get,
    argumentProvider, isPossible)
}

case class Redo(handler: Handler[Unit],
                argumentProvider: ArgumentProvider[Unit],
                isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object Redo extends EventCompanion[Redo, Unit] {
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): Redo = Redo(handler.get,
    argumentProvider, isPossible)
}

case class Exit(handler: Handler[Unit],
                argumentProvider: ArgumentProvider[Unit],
                isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object Exit extends EventCompanion[Exit, Unit] {
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): Exit = Exit(handler.get,
    argumentProvider, isPossible)
}

case class Save(handler: Handler[Unit],
                argumentProvider: ArgumentProvider[Unit],
                isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object Save extends EventCompanion[Save, Unit] {
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): Save = Save(handler.get,
    argumentProvider, isPossible)
}

case class SaveAndExit(handler: Handler[Unit],
                       argumentProvider: ArgumentProvider[Unit],
                       isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object SaveAndExit extends EventCompanion[SaveAndExit, Unit] {
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): SaveAndExit = SaveAndExit(handler.get,
    argumentProvider, isPossible)
}

// Global Events
case class AddTask(handler: Handler[Unit],
                   argumentProvider: ArgumentProvider[Unit],
                   isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object AddTask extends EventCompanion[AddTask, Unit] {
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): AddTask = AddTask(handler.get,
    argumentProvider, isPossible)
}

case class RemoveTask(handler: Handler[UUID],
                      argumentProvider: ArgumentProvider[UUID],
                      isPossible: () => Boolean)
  extends Event[UUID](handler, argumentProvider, isPossible)
case object RemoveTask extends EventCompanion[RemoveTask, UUID] {
  override protected def create(argumentProvider: ArgumentProvider[UUID],
                                isPossible: () => Boolean): RemoveTask = RemoveTask(handler.get,
    argumentProvider, isPossible)
}

case class EditTask(handler: Handler[UUID],
                    argumentProvider: ArgumentProvider[UUID],
                    isPossible: () => Boolean)
  extends Event[UUID](handler, argumentProvider, isPossible)
case object EditTask extends EventCompanion[EditTask, UUID] {
  override protected def create(argumentProvider: ArgumentProvider[UUID],
                                isPossible: () => Boolean): EditTask = EditTask(handler.get,
    argumentProvider, isPossible)
}

case class AddTag(handler: Handler[Unit],
                  argumentProvider: ArgumentProvider[Unit],
                  isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object AddTag extends EventCompanion[AddTag, Unit] {
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): AddTag = AddTag(handler.get,
    argumentProvider, isPossible)
}

case class RemoveTag(handler: Handler[UUID],
                     argumentProvider: ArgumentProvider[UUID],
                     isPossible: () => Boolean)
  extends Event[UUID](handler, argumentProvider, isPossible)
case object RemoveTag extends EventCompanion[RemoveTag, UUID] {
  override protected def create(argumentProvider: ArgumentProvider[UUID],
                                isPossible: () => Boolean): RemoveTag = RemoveTag(handler.get,
    argumentProvider, isPossible)
}

case class EditTag(handler: Handler[UUID],
                   argumentProvider: ArgumentProvider[UUID],
                   isPossible: () => Boolean)
  extends Event[UUID](handler, argumentProvider, isPossible)
case object EditTag extends EventCompanion[EditTag, UUID] {
  override protected def create(argumentProvider: ArgumentProvider[UUID],
                                isPossible: () => Boolean): EditTag = EditTag(handler.get,
    argumentProvider, isPossible)
}

case class AddPriority(handler: Handler[Unit],
                       argumentProvider: ArgumentProvider[Unit],
                       isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object AddPriority extends EventCompanion[AddPriority, Unit] {
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): AddPriority = AddPriority(handler.get,
    argumentProvider, isPossible)
}

case class RemovePriority(handler: Handler[UUID],
                          argumentProvider: ArgumentProvider[UUID],
                          isPossible: () => Boolean)
  extends Event[UUID](handler, argumentProvider, isPossible)
case object RemovePriority extends EventCompanion[RemovePriority, UUID] {
  override protected def create(argumentProvider: ArgumentProvider[UUID],
                                isPossible: () => Boolean): RemovePriority = RemovePriority(handler.get,
    argumentProvider, isPossible)
}

case class EditPriority(handler: Handler[UUID],
                        argumentProvider: ArgumentProvider[UUID],
                        isPossible: () => Boolean)
  extends Event[UUID](handler, argumentProvider, isPossible)
case object EditPriority extends EventCompanion[EditPriority, UUID] {
  override protected def create(argumentProvider: ArgumentProvider[UUID],
                                isPossible: () => Boolean): EditPriority = EditPriority(handler.get,
    argumentProvider, isPossible)
}

case class AddState(handler: Handler[Unit],
                    argumentProvider: ArgumentProvider[Unit],
                    isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object AddState extends EventCompanion[AddState, Unit] {
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): AddState = AddState(handler.get,
    argumentProvider, isPossible)
}

case class RemoveState(handler: Handler[UUID],
                       argumentProvider: ArgumentProvider[UUID],
                       isPossible: () => Boolean)
  extends Event[UUID](handler, argumentProvider, isPossible)
case object RemoveState extends EventCompanion[RemoveState, UUID] {
  override protected def create(argumentProvider: ArgumentProvider[UUID],
                                isPossible: () => Boolean): RemoveState = RemoveState(handler.get,
    argumentProvider, isPossible)
}

case class EditState(handler: Handler[UUID],
                     argumentProvider: ArgumentProvider[UUID],
                     isPossible: () => Boolean)
  extends Event[UUID](handler, argumentProvider, isPossible)
case object EditState extends EventCompanion[EditState, UUID] {
  override protected def create(argumentProvider: ArgumentProvider[UUID],
                                isPossible: () => Boolean): EditState = EditState(handler.get,
    argumentProvider, isPossible)
}

case class ChangeView(handler: Handler[ViewType],
                      argumentProvider: ArgumentProvider[ViewType],
                      isPossible: () => Boolean)
  extends Event[ViewType](handler, argumentProvider, isPossible)
case object ChangeView extends EventCompanion[ChangeView, ViewType] {
  override protected def create(argumentProvider: ArgumentProvider[ViewType],
                                isPossible: () => Boolean): ChangeView = ChangeView(handler.get,
    argumentProvider, isPossible)
}

//case class ChangeTheme() extends Event[]

//case class ChangeDataFileType() extends Event

//case class ChangeDataFilePath() extends Event
