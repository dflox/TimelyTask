package me.timelytask.view.eventHandlers

import com.github.nscala_time.time.Imports.{Period, richInt}
import me.timelytask.controller.commands.{Command, CommandHandler}
import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
import me.timelytask.model.utility.{InputError, TimeSelection}
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.view.events.*
import me.timelytask.view.events.argwrapper.ViewChangeArgumentWrapper
import me.timelytask.view.viewmodel.elemts.{FocusDirection, Focusable, TaskCollection}
import me.timelytask.view.viewmodel.viewchanger.TaskEditViewChangeArg
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel, ViewModel}

import java.util.concurrent.LinkedBlockingQueue
import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

class CalendarEventHandler(calendarViewModelPublisher: Publisher[CalendarViewModel],
                           modelPublisher: Publisher[Model],
                           undoManager: CommandHandler,
                           activeViewPublisher: Publisher[ViewType],
                           commandQueue: LinkedBlockingQueue[Command[?]])
  extends EventHandler[CALENDAR, CalendarViewModel](
    calendarViewModelPublisher,
    modelPublisher,
    undoManager,
    activeViewPublisher,
    commandQueue) {

  val viewModel: () => Option[CalendarViewModel] = () => calendarViewModelPublisher.getValue

  override def init(): Unit = {

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
    },
      (args: FocusDirection) => if viewModel().isEmpty | viewModel().get.getFocusElementGrid.isEmpty
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

    EditFocusedTask.setHandler((args: Option[CalendarViewModel])
    => {
      if args.isEmpty then false
      else {
        val task = args.get.getTaskToEdit
        if changeView.isEmpty then initChangeView()
        changeView.get.call[TASKEdit](ViewChangeArgumentWrapper[TASKEdit, ViewModel[TASKEdit,
          TaskEditViewModel], TaskEditViewChangeArg](TaskEditViewChangeArg(task, Some(CALENDAR))))
        match
          case None => true
          case Some(value) => false
      }
    }, (args: Option[CalendarViewModel]) => {
      if args.isEmpty | args.get.getTaskToEdit.isEmpty then Some(InputError("No task to edit."))
      else None
    })

  }

  //  ChangeView.addHandler[CALENDAR](new TypeSensitiveHandler[CALENDAR,
  //    ViewChangeArgument[CALENDAR, CalendarViewModel],
  //    ViewChangeArgumentWrapper[CALENDAR, CalendarViewModel, CalendarViewChangeArg]] {
  //    override def typeTag: ClassTag[CALENDAR] = implicitly[ClassTag[CALENDAR]]
  //    protected def isPossible(args: ViewChangeArgumentWrapper[ViewType, ?, ?])
  //    : Option[InputError] = args.arg match
  //      case arg: CalendarViewChangeArg => None
  //      case _ => Some(InputError("Cannot change view to calendar view."))
  //    protected def call(args: ViewChangeArgumentWrapper[ViewType, ?, ?]): Boolean = args.arg 
  //    match
  //      case viewChangeArg: CalendarViewChangeArg =>
  //        calendarViewModelPublisher.update(viewChangeArg(calendarViewModelPublisher.getValue))
  //        activeViewPublisher.update(Some(CALENDAR))
  //        true
  //      case _ => false
  //  })

  protected var changeView: Option[ChangeView] = None

  private def initChangeView: () => Unit = () => changeView = Some(ChangeView.createEvent)

  private def modelUpdate(model: Option[Model]): Boolean = {
    if model.isEmpty then return false
    calendarViewModelPublisher.getValue match
      case Some(viewModel) =>
        Some(CalendarViewModel(viewModel.timeSelection, modelPublisher))
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
