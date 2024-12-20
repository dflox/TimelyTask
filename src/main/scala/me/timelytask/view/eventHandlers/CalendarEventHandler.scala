package me.timelytask.view.eventHandlers

import com.github.nscala_time.time.Imports.{DateTime, Period, richInt}
import me.timelytask.controller.commands.UndoManager
import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
import me.timelytask.model.utility.{InputError, TimeSelection}
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.view.events.*
import me.timelytask.view.events.argwrapper.ViewChangeArgumentWrapper
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}
import me.timelytask.view.viewmodel.elemts.{FocusDirection, Focusable, TaskCollection}
import me.timelytask.view.viewmodel.viewchanger.{CalendarViewChangeArg, TaskEditViewChangeArg, ViewChangeArgument}
import me.timelytask.view.viewmodel.viewchanger.ViewChangeArgument

import scala.util.{Failure, Success, Try}

class CalendarEventHandler(using calendarViewModelPublisher: Publisher[CalendarViewModel],
                           modelPublisher: Publisher[Model],
                           undoManager: UndoManager,
                           activeViewPublisher: Publisher[ViewType])
  extends EventHandler[CALENDAR, CalendarViewModel] {

  val viewModel: () => Option[CalendarViewModel] = () => calendarViewModelPublisher.getValue

  NextDay.setHandler((args: Unit) => {
    addPeriodToTimeSelection(1.days)
  }, (args: Unit) => None)

  PreviousDay.setHandler((args: Unit) => {
    addPeriodToTimeSelection(-1.days)
  }, (args: Unit) => None)

  NextWeek.setHandler((args: Unit) => {
    addPeriodToTimeSelection(1.weeks)
  }, (args: Unit) => None)

  PreviousWeek.setHandler((args: Unit) => {
    addPeriodToTimeSelection(-1.weeks)
  }, (args: Unit) => None)

  GoToToday.setHandler((args: Unit) => {
    Try[CalendarViewModel] {
      viewModel().get.copy(timeSelection = viewModel().get.timeSelection.startingToday)
    } match
      case Success(value) => Some(value)
      case Failure(exception) => None
  }, (args: Unit) => None)

  ShowWholeWeek.setHandler((args: Unit) => {
    Try[CalendarViewModel] {
      viewModel().get.copy(timeSelection = viewModel().get.timeSelection.wholeWeek)
    } match
      case Success(value) => Some(value)
      case Failure(exception) => None
  }, (args: Unit) => None)

  ShowMoreDays.setHandler((args: Unit) => {
    addDaysToTimeSelection(1)
  }, (args: Unit) => None)

  ShowLessDays.setHandler((args: Unit) => {
    addDaysToTimeSelection(-1)
  }, (args: Unit) => None)

  GoToDate.setHandler((args: Unit) => {
    // TODO: Implement GoToDate event, needs custom Dialog in ViewModel triggered by Eventhandler
    //  - not yet implemented in current ViewModel
    false
  }, (args: Unit) => None)

  // Focus Events
  MoveFocus.addHandler[CALENDAR]((args: FocusDirection) => {
    Try[CalendarViewModel] {
      viewModel().get.copy(focusElementGrid = viewModel().get.getFocusElementGrid.get.moveFocus
        (args))
    } match
      case Success(value) => Some(value)
      case Failure(exception) => None
  }, (args: FocusDirection) => if viewModel().isEmpty | viewModel().get.getFocusElementGrid.isEmpty
                               then
                                 Some(InputError("Fatal Error: No focus element grid defined!"))
                               else
                                 None)

  SetFocusTo.addHandler[CALENDAR]((args: Task) => {
    Try[CalendarViewModel] {
      viewModel().get.copy(
        focusElementGrid = viewModel().get.getFocusElementGrid.get.setFocusToElement
          (selectFunc = {
            case Some(taskCollection: TaskCollection) => taskCollection.getTasks.contains(args)
            case _ => false
          }))
    } match
      case Success(value) => Some(value)
      case Failure(exception) => None
  }, (args: Task) => if viewModel().isEmpty | viewModel().get.getFocusElementGrid.isEmpty then
                       Some(InputError("Fatal Error: No focus element grid defined!"))
                     else
                       None)

  protected var changeView: Option[ChangeView] = None
  private def initChangeView: () => Unit = () => changeView = Some(ChangeView.createEvent)

  EditFocusedTask.setHandler((args: Option[CalendarViewModel]) => {
    if args.isEmpty then false
    else {val task = args.get.getTaskToEdit
    if changeView.isEmpty then initChangeView()
    changeView.get.call[TASKEdit](ViewChangeArgumentWrapper[TASKEdit]
      (TaskEditViewChangeArg(task, Some(CALENDAR))))
  }}, (args: Option[CalendarViewModel]) => {
    if args.isEmpty | args.get.getTaskToEdit.isEmpty then Some(InputError("No task to edit."))
    else None
  })

  ChangeView.addHandler({
    case viewChangeArg: ViewChangeArgument[CALENDAR, CalendarViewModel] =>
      calendarViewModelPublisher.update(viewChangeArg(calendarViewModelPublisher.getValue))
      activeViewPublisher.update(Some(CALENDAR))
      true
    case _ => false
  }, (args: ViewChangeArgumentWrapper[ViewType]) => args.arg match
    case arg: CalendarViewChangeArg => None
    case _ => Some(InputError("Cannot change view to calendar view."))
  )

  private def modelUpdate(model: Option[Model]): Boolean = {
    if model.isEmpty then return false
    calendarViewModelPublisher.getValue match
      case Some(viewModel) =>
        Some(CalendarViewModel(viewModel.timeSelection,modelPublisher))
      case None => Some(CalendarViewModel(modelPublisher = modelPublisher))
  }

  modelPublisher.addListener(modelUpdate)

  private def addDaysToTimeSelection(daysToAdd: Int): Option[CalendarViewModel]
  = {
    Try[CalendarViewModel] {
      viewModel().get.copy(timeSelection = viewModel().get.timeSelection.addDayCount
        (daysToAdd).get)
    } match
      case Success(value) => Some(value)
      case Failure(exception) => None
  }

  private def addPeriodToTimeSelection(period: Period): Option[CalendarViewModel] = {
    Try[CalendarViewModel] {
      viewModel().get.copy(timeSelection = viewModel().get.timeSelection + period)
    } match
      case Success(value) => Some(value)
      case Failure(exception) => None
  }
}
