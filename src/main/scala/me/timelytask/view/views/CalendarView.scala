package me.timelytask.view.views

import me.timelytask.model.Task
import me.timelytask.model.settings.CALENDAR
import me.timelytask.model.utility.{Key, Space}
import me.timelytask.util.Publisher
import me.timelytask.view.events.*
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.dialogmodel.OptionDialogModel
import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}

trait CalendarView[RenderType] extends View[CALENDAR, CalendarViewModel, RenderType]{
  val nextDay: NextDay = NextDay.createEvent
  val previousDay: PreviousDay = PreviousDay.createEvent
  val nextWeek: NextWeek = NextWeek.createEvent
  val previousWeek: PreviousWeek = PreviousWeek.createEvent
  val goToToday: GoToToday = GoToToday.createEvent
  val goToDate: GoToDate = GoToDate.createEvent
  val showWholeWeek: ShowWholeWeek = ShowWholeWeek.createEvent
  val showMoreDays: ShowMoreDays = ShowMoreDays.createEvent
  val showLessDays: ShowLessDays = ShowLessDays.createEvent
  
  val editFocusedTask: EditFocusedTask = EditFocusedTask.createEvent 

  def renderOptionDialog: (optionDialogModel: Option[OptionDialogModel[Task]],
                           renderType: Option[RenderType]) => Option[Task]
  
  override def handleKey(key: Key): Boolean = {
    if key == Space then {
      editFocusedTask.call(viewModel.interact[RenderType](currentlyRendered,
        renderOptionDialog))
    }
    else keymapPublisher.getValue.handleKey(key, this)
  }
}
