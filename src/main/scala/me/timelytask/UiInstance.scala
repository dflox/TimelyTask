package me.timelytask

import me.timelytask.controller.commands.{Command, CommandHandler}
import me.timelytask.model.Model
import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.eventHandlers.{CalendarEventHandler, GlobalEventHandler, TaskEditEventHandler}
import me.timelytask.view.keymaps.{CalendarViewResolver, Keymap, TaskEditViewResolver}
import me.timelytask.view.tui.TUIManager
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}
import me.timelytask.view.views.View

import java.util.concurrent.LinkedBlockingQueue

class UiInstance(modelPublisher: PublisherImpl[Model], undoManager: CommandHandler,
                 commandQueue: LinkedBlockingQueue[Command[?]]) {

  val activeViewPublisher: PublisherImpl[ViewType] = PublisherImpl[ViewType](Some(CALENDAR))

  val calendarViewModelPublisher: PublisherImpl[CalendarViewModel] = PublisherImpl[CalendarViewModel](
    None)

  val taskEditViewModelPublisher: PublisherImpl[TaskEditViewModel] = PublisherImpl[TaskEditViewModel](
    None)

  val calendarKeyMapPublisher: PublisherImpl[Keymap[CALENDAR, CalendarViewModel, View[CALENDAR,
    CalendarViewModel, ?]]] = PublisherImpl[Keymap[CALENDAR, CalendarViewModel, View[CALENDAR,
    CalendarViewModel, ?]]](None)

  val taskEditKeyMapPublisher: PublisherImpl[Keymap[TASKEdit, TaskEditViewModel, View[TASKEdit,
    TaskEditViewModel, ?]]] = PublisherImpl[Keymap[TASKEdit, TaskEditViewModel, View[TASKEdit,
    TaskEditViewModel, ?]]](None)

  val calendarEventHandler: CalendarEventHandler = new CalendarEventHandler(
    calendarViewModelPublisher, modelPublisher, undoManager, activeViewPublisher, commandQueue)

  val taskEditEventHandler: TaskEditEventHandler = new TaskEditEventHandler(
    taskEditViewModelPublisher, modelPublisher, undoManager, activeViewPublisher, commandQueue)

  val globalEventHandler: GlobalEventHandler = new GlobalEventHandler(calendarViewModelPublisher,
    taskEditViewModelPublisher, modelPublisher, undoManager, activeViewPublisher)

  def run(): Unit = {
    init()
    val tuiManager: TUIManager = TUIManager(activeViewPublisher, calendarKeyMapPublisher,
      calendarViewModelPublisher, taskEditKeyMapPublisher, taskEditViewModelPublisher)
    //val uiThread = new ApplicationThread[Unit]()
    //uiThread.run(tuiManager.run()).await()
    tuiManager.run()
  }

  private def init(): Unit = {
    calendarEventHandler.init()
    taskEditEventHandler.init()
    
    if modelPublisher.getValue.isEmpty then return
    calendarKeyMapPublisher.update(Some(Keymap[CALENDAR, CalendarViewModel, View[CALENDAR,
      CalendarViewModel, ?]](modelPublisher.getValue.get.config.keymaps(CALENDAR), new
        CalendarViewResolver())))

    taskEditKeyMapPublisher.update(Some(Keymap[TASKEdit, TaskEditViewModel, View[TASKEdit,
      TaskEditViewModel, ?]](modelPublisher.getValue.get.config.keymaps(TASKEdit), new
        TaskEditViewResolver())))
  }
}
