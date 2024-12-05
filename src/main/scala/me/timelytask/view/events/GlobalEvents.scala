package me.timelytask.view.events

import me.timelytask.model.settings.ViewType

import java.util.UUID

//App Events
case class Undo() extends Event[Unit]
case object Undo extends EventCompanion[Undo, Unit]

case class Redo() extends Event[Unit]
case object Redo extends EventCompanion[Redo, Unit]

case class Exit() extends Event[Unit]
case object Exit extends EventCompanion[Exit, Unit]

case class Save() extends Event[Unit]
case object Save extends EventCompanion[Save, Unit]

case class SaveAndExit() extends Event[Unit]
case object SaveAndExit extends EventCompanion[SaveAndExit, Unit]

// Global Events

case class AddTask() extends Event[Unit]
case object AddTask extends EventCompanion[AddTask, Unit]

case class RemoveTask() extends Event[UUID]
case object RemoveTask extends EventCompanion[RemoveTask, UUID]

case class EditTask() extends Event[UUID]
case object EditTask extends EventCompanion[EditTask, UUID]

case class AddTag() extends Event[Unit]
case object AddTag extends EventCompanion[AddTag, Unit]

case class RemoveTag() extends Event[UUID]
case object RemoveTag extends EventCompanion[RemoveTag, UUID]

case class EditTag() extends Event[UUID]
case object EditTag extends EventCompanion[EditTag, UUID]

case class AddPriority() extends Event[Unit]
case object AddPriority extends EventCompanion[AddPriority, Unit]

case class RemovePriority() extends Event[UUID]
case object RemovePriority extends EventCompanion[RemovePriority, UUID]

case class EditPriority() extends Event[UUID]
case object EditPriority extends EventCompanion[EditPriority, UUID]

case class AddState() extends Event[Unit]
case object AddState extends EventCompanion[AddState, Unit]

case class RemoveState() extends Event[UUID]
case object RemoveState extends EventCompanion[RemoveState, UUID]

case class EditState() extends Event[UUID]
case object EditState extends EventCompanion[EditState, UUID]

case class ChangeView() extends Event[ViewType]
case object ChangeView extends EventCompanion[ChangeView, ViewType]

//case class ChangeTheme() extends Event[]

//case class ChangeDataFileType() extends Event

//case class ChangeDataFilePath() extends Event
