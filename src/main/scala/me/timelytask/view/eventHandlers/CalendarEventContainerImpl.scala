package me.timelytask.view.eventHandlers

import com.github.nscala_time.time.Imports.{Period, richInt}
import com.softwaremill.macwire.autowire
import me.timelytask.controller.commands.{Command, CommandHandler}
import me.timelytask.core.CoreModule
import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
import me.timelytask.model.utility.{InputError, TimeSelection}
import me.timelytask.model.{Model, Task}
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.events.*
import me.timelytask.view.events.argwrapper.ViewChangeArgumentWrapper
import me.timelytask.view.viewmodel.elemts.{FocusDirection, Focusable, TaskCollection}
import me.timelytask.view.viewmodel.viewchanger.TaskEditViewChangeArg
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel, ViewModel}

import java.util.concurrent.LinkedBlockingQueue
import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

class CalendarEventContainerImpl(calendarViewModelPublisher: Publisher[CalendarViewModel],
                                 activeViewPublisher: Publisher[ViewType],
                                 eventHandler: EventHandler,
                                 coreModule: CoreModule)
  extends CalendarEventContainer
  with EventContainer(
    calendarViewModelPublisher,
    activeViewPublisher,
    eventHandler,
    coreModule) {
  
  def nextDay(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => addPeriodToTimeSelection(1.days),
    (args: Unit) => None,
    ()
  ){})

  def previousDay(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => addPeriodToTimeSelection(-1.days),
    (args: Unit) => None,
    ()
  ){})

  def nextWeek(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => addPeriodToTimeSelection(1.weeks),
    (args: Unit) => None,
    ()
  ){})

  def previousWeek(): Unit = eventHandler.handle(new Event[Unit](
    (args: Unit) => addPeriodToTimeSelection(-1.weeks),
    (args: Unit) => None,
    ()
  ){})


//  def initi(): Unit = {
//
//    GoToToday.setHandler((args: Unit) => {
//      Try[CalendarViewModel] {
//        viewModel().get.copy(timeSelection = viewModel().get.timeSelection.startingToday)
//      } match
//        case Success(value) => Some(value)
//        case Failure(exception) => None
//    }, (args: Unit) => None)
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
//      // TODO: Implement GoToDate event, needs custom Dialog in ViewModel triggered by Eventhandler
//      //  - not yet implemented in current ViewModel
//      false
//    }, (args: Unit) => None)
//
//    // Focus Events
//    MoveFocus.addHandler[CALENDAR]((args: FocusDirection) => {
//      Try[CalendarViewModel] {
//        viewModel().get.copy(focusElementGrid = viewModel().get.getFocusElementGrid.get.moveFocus
//          (args))
//      } match
//        case Success(value) => Some(value)
//        case Failure(exception) => None
//    },
//      (args: FocusDirection) => if viewModel().isEmpty | viewModel().get.getFocusElementGrid.isEmpty
//                                then
//                                  Some(InputError("Fatal Error: No focus element grid defined!"))
//                                else
//                                  None)
//
//    SetFocusTo.addHandler[CALENDAR]((args: Task) => {
//      Try[CalendarViewModel] {
//        viewModel().get.copy(
//          focusElementGrid = viewModel().get.getFocusElementGrid.get.setFocusToElement
//            (selectFunc = {
//              case Some(taskCollection: TaskCollection) => taskCollection.getTasks.contains(args)
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
//          TaskEditViewModel], TaskEditViewChangeArg](TaskEditViewChangeArg(task, Some(CALENDAR))))
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
    if model.isEmpty then return true
    viewModel() match {
      case Some(vm) => Some(CalendarViewModel(vm.timeSelection, model.get))
      case None => Some(CalendarViewModel(TimeSelection.defaultTimeSelection, model.get))
    }
  }
  
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
