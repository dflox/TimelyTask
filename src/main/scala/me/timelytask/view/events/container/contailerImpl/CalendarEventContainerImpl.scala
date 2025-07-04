package me.timelytask.view.events.container.contailerImpl

import com.github.nscala_time.time.Imports.{Period, richInt}
import me.timelytask.core.CoreModule
import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.model.utility.TimeSelection
import me.timelytask.util.Publisher
import me.timelytask.view.events.*
import me.timelytask.view.events.container.CalendarEventContainer
import me.timelytask.view.events.event.Event
import me.timelytask.view.viewmodel.CalendarViewModel

import scala.util.{Failure, Success, Try}

class CalendarEventContainerImpl(calendarViewModelPublisher: Publisher[CalendarViewModel],
                                 activeViewPublisher: Publisher[ViewType],
                                 eventHandler: EventHandler,
                                 coreModule: CoreModule,
                                 userToken: String)
  extends CalendarEventContainer
  with EventContainer(
    calendarViewModelPublisher,
    activeViewPublisher,
    eventHandler,
    coreModule,
    userToken) {

  def nextDay(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => addPeriodToTimeSelection(1.days),
    (args: Unit) => None,
    ()
  ) {})

  def previousDay(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => addPeriodToTimeSelection(-1.days),
    (args: Unit) => None,
    ()
  ) {})

  def nextWeek(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => addPeriodToTimeSelection(1.weeks),
    (args: Unit) => None,
    ()
  ) {})

  def previousWeek(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => addPeriodToTimeSelection(-1.weeks),
    (args: Unit) => None,
    ()
  ) {})


  //  def initi(): Unit = {
  //
  //    ShowWholeWeek.setHandler((args: Unit) => {
  //      Try[CalendarViewModel] {
  //        viewModel().get.copy(timeSelection = viewModel().get.timeSelection.wholeWeek)
  //      } match
  //        case Success(value) => Some(value)
  //        case Failure(exception) => None
  //    }, (args: Unit) => None)
  //
  //    ShowMoreDays.setHandler((args: Unit) => {
  //      addDaysToTimeSelection(1)
  //    }, (args: Unit) => None)
  //
  //    ShowLessDays.setHandler((args: Unit) => {
  //      addDaysToTimeSelection(-1)
  //    }, (args: Unit) => None)
  //
  //    GoToDate.setHandler((args: Unit) => {
  //      // TODO: Implement GoToDate event, needs custom Dialog in ViewModel triggered by 
  //       Eventhandler
  //      //  - not yet implemented in current ViewModel
  //      false
  //    }, (args: Unit) => None)
  //
  //    // Focus Events
  //    MoveFocus.addHandler[CALENDAR]((args: FocusDirection) => {
  //      Try[CalendarViewModel] {
  //        viewModel().get.copy(focusElementGrid = viewModel().get.getFocusElementGrid.get
  //        .moveFocus
  //          (args))
  //      } match
  //        case Success(value) => Some(value)
  //        case Failure(exception) => None
  //    },
  //      (args: FocusDirection) => if viewModel().isEmpty | viewModel().get.getFocusElementGrid
  //      .isEmpty
  //                                then
  //                                  Some(InputError("Fatal Error: No focus element grid 
  //                                  defined!"))
  //                                else
  //                                  None)
  //
  //    SetFocusTo.addHandler[CALENDAR]((args: Task) => {
  //      Try[CalendarViewModel] {
  //        viewModel().get.copy(
  //          focusElementGrid = viewModel().get.getFocusElementGrid.get.setFocusToElement
  //            (selectFunc = {
  //              case Some(taskCollection: TaskCollection) => taskCollection.getTasks.contains
  //              (args)
  //              case _ => false
  //            }))
  //      } match
  //        case Success(value) => Some(value)
  //        case Failure(exception) => None
  //    }, (args: Task) => if viewModel().isEmpty | viewModel().get.getFocusElementGrid.isEmpty then
  //                         Some(InputError("Fatal Error: No focus element grid defined!"))
  //                       else
  //                         None)
  //
  //    EditFocusedTask.setHandler((args: Option[CalendarViewModel])
  //    => {
  //      if args.isEmpty then false
  //      else {
  //        val task = args.get.getTaskToEdit
  //        if changeView.isEmpty then initChangeView()
  //        changeView.get.call[TASKEdit](ViewChangeArgumentWrapper[TASKEdit, ViewModel[TASKEdit,
  //          TaskEditViewModel], TaskEditViewChangeArg](TaskEditViewChangeArg(task, Some
  //          (CALENDAR))))
  //        match
  //          case None => true
  //          case Some(value) => false
  //      }
  //    }, (args: Option[CalendarViewModel]) => {
  //      if args.isEmpty | args.get.getTaskToEdit.isEmpty then Some(InputError("No task to edit."))
  //      else None
  //    })
  //
  //  }

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

  //  protected var changeView: Option[ChangeView] = None
  //
  //  private def initChangeView: () => Unit = () => changeView = Some(ChangeView.createEvent)

  protected override def updateModel(model: Option[Model]): Boolean = {
    if model.isEmpty then true
    else {
      viewModel() match {
        case Some(vm) => Some(CalendarViewModel(vm.timeSelection, model.get))
        case None => Some(CalendarViewModel(TimeSelection.defaultTimeSelection, model.get))
      }
    }
  }

  private def addDaysToTimeSelection(daysToAdd: Int): Option[CalendarViewModel]
  = {
    Try[CalendarViewModel] {
      viewModel().get.copy(timeSelection = viewModel().get.timeSelection.addDayCount
        (daysToAdd).get, focusElementGrid = None)
    } match
      case Success(value) => Some(value)
      case Failure(exception) => None
  }

  private def addPeriodToTimeSelection(period: Period): Option[CalendarViewModel] = {
    Try[CalendarViewModel] {
      viewModel().get.copy(timeSelection = viewModel().get.timeSelection + period,
        focusElementGrid = None)
    } match
      case Success(value) => Some(value)
      case Failure(exception) => None
  }

  override def goToToday(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => {
      Try[CalendarViewModel] {
        viewModel().get.copy(timeSelection = viewModel().get.timeSelection.startingToday,
          focusElementGrid = None)
      } match {
        case Success(value) => Some(value)
        case Failure(exception) => None
      }
    },
    (args: Unit) => None,
    ()
  ) {})
}
