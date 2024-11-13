package me.timelytask.model.settings

sealed trait Action{
  private var handler: Option[() => Boolean] = None
  def setHandler(newHandler: () => Boolean): Unit = {
    if(handler.isDefined) throw new Exception("Handler already set")
    handler = Some(newHandler)
  }

  def call: Boolean = {
    handler match {
      case Some(h) => h()
      case None => false
    }
  }
}

// Focus actions
case object FocusPrevious extends Action

case object FocusNext extends Action

case object FocusUp extends Action

case object FocusDown extends Action

case object FocusLeft extends Action

case object FocusRight extends Action

// Calendar actions
case object NextDay extends Action

case object PreviousDay extends Action

case object NextWeek extends Action

case object PreviousWeek extends Action

case object GoToToday extends Action

case object GoToDate extends Action

case object ShowWholeWeek extends Action

case object ShowMoreDays extends Action

case object ShowLessDays extends Action

// Taskview actions
case object NextField extends Action

case object PreviousField extends Action

// Universal Model actions
case object AddTask extends Action

case object RemoveTask extends Action

case object EditTask extends Action

case object AddTag extends Action

case object RemoveTag extends Action

case object EditTag extends Action

case object AddPriority extends Action

case object RemovePriority extends Action

case object EditPriority extends Action

case object AddState extends Action

case object RemoveState extends Action

case object EditState extends Action

case object ChangeView extends Action

case object ChangeTheme extends Action

case object ChangeDataFileType extends Action

case object ChangeDataFilePath extends Action

// App actions
case object SaveData extends Action

case object LoadData extends Action

case object Undo extends Action

case object Redo extends Action

case object Exit extends Action

case object SaveAndExit extends Action

case object OpenSettings extends Action

case object StartApp extends Action

case object Help extends Action

case object About extends Action

// other
case object NoAction extends Action