package me.timelytask.model.settings

import me.timelytask.view.events.Event

// Taskview actions
case class NextField() extends Event

case class PreviousField() extends Event

case class SaveTask() extends Event

// Universal Model actions
case class AddTask() extends Event

case class RemoveTask() extends Event

case class EditTask() extends Event

case class AddTag() extends Event

case class RemoveTag() extends Event

case class EditTag() extends Event

case class AddPriority() extends Event

case class RemovePriority() extends Event

case class EditPriority() extends Event

case class AddState() extends Event

case class RemoveState() extends Event

case class EditState() extends Event

case class ChangeView() extends Event

case class ChangeTheme() extends Event

case class ChangeDataFileType() extends Event

case class ChangeDataFilePath() extends Event

// App actions
case class Undo() extends Event

case class Redo() extends Event

case class Exit() extends Event

case class SaveAndExit() extends Event

case class OpenSettings() extends Event

case class Help() extends Event

case class About() extends Event

// other
case class NoEvent() extends Event