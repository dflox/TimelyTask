package me.timelytask.view.eventHandlers

import com.github.nscala_time.time.Imports.richInt
import me.timelytask.controller.commands.UndoManager
import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.model.utility.{InputError, TimeSelection}
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.view.events.*
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.viewmodel.elemts.FocusDirection.*
import me.timelytask.view.viewmodel.elemts.{FocusDirection, Focusable, TaskCollection}

class CalendarEventHandler(using calendarViewModelPublisher: Publisher[CalendarViewModel],
                           modelPublisher: Publisher[Model],
                           undoManager: UndoManager,
                           activeViewPublisher: Publisher[ViewType])
  extends EventHandler[CALENDAR, CalendarViewModel] {

  val viewModel: () => CalendarViewModel = () => calendarViewModelPublisher.getValue
    .asInstanceOf[CalendarViewModel]

  NextDay.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection + 1.days))
  }, (args: Unit) => None)

  PreviousDay.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection - 1.days))
  }, (args: Unit) => None)

  NextWeek.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection + 1.weeks))
  }, (args: Unit) => None)

  PreviousWeek.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection - 1.weeks))
  }, (args: Unit) => None)

  GoToToday.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection.startingToday))
  }, (args: Unit) => None)

  ShowWholeWeek.setHandler((args: Unit) => {
    Some(viewModel().copy(timeSelection = viewModel().timeSelection.currentWeek))
  }, (args: Unit) => None)

  ShowMoreDays.setHandler((args: Unit) => {
    changeTimeSelection(viewModel().timeSelection.addDayCount(1))
  }, (args: Unit) => None)

  ShowLessDays.setHandler((args: Unit) => {
    changeTimeSelection(viewModel().timeSelection.subtractDayCount(1))
  }, (args: Unit) => None)

  // Focus Events
  MoveFocus.setHandler((args: FocusDirection) => {
    viewModel().getFocusElementGrid match
      case Some(focusElementGrid) => Some(viewModel().copy(focusElementGrid = focusElementGrid
        .moveFocus(args)))
      case None => None
  }, (args: FocusDirection) => if viewModel().getFocusElementGrid.isEmpty then
                                 Some(InputError("Fatal Error: No focus element grid defined!"))
                               else
                                 None)

  SetFocusTo.setHandler((args: Task) => {
    viewModel().getFocusElementGrid match
      case Some(focusElementGrid) => Some(viewModel().copy(focusElementGrid = focusElementGrid
        .setFocusToElement((element: Option[Focusable]) => element match {
          case Some(element) => element match
            case taskCollection: TaskCollection => taskCollection.getTasks.contains(args)
            case _ => false
          case None => false
        })))
      case None => None
  }, (args: Task) => if viewModel().getFocusElementGrid.isEmpty then
                       Some(InputError("Fatal Error: No focus element grid defined!"))
                     else
                       None)

  private def changeTimeSelection(timeSelection: Option[TimeSelection]): Option[CalendarViewModel]
  = {
    timeSelection match {
      case Some(value) => Some(viewModel().copy(timeSelection = value))
      case None => None
    }
  }
}
