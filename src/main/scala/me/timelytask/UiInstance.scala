package me.timelytask

import me.timelytask.controller.commands.{Command, CommandHandler}
import me.timelytask.model.Model
import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.eventHandlers.{CalendarEventHandler, GlobalEventHandler, TaskEditEventHandler}
import me.timelytask.view.keymaps.{CalendarViewResolver, Keymap, TaskEditViewResolver}
import me.timelytask.view.tui.TUIManager
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}
import me.timelytask.view.views.View

import java.util.concurrent.LinkedBlockingQueue

class UiInstance(modelPublisher: Publisher[Model], undoManager: CommandHandler, 
                 commandQueue: LinkedBlockingQueue[Command[?]]) {

  val activeViewPublisher: Publisher[ViewType] = Publisher[ViewType](Some(CALENDAR))

  val calendarViewModelPublisher: Publisher[CalendarViewModel] = Publisher[CalendarViewModel](
    None)

  val taskEditViewModelPublisher: Publisher[TaskEditViewModel] = Publisher[TaskEditViewModel](
    None)

  val calendarKeyMapPublisher: Publisher[Keymap[CALENDAR, CalendarViewModel, View[CALENDAR,
    CalendarViewModel, ?]]] = Publisher[Keymap[CALENDAR, CalendarViewModel, View[CALENDAR,
    CalendarViewModel, ?]]](None)

  val taskEditKeyMapPublisher: Publisher[Keymap[TASKEdit, TaskEditViewModel, View[TASKEdit,
    TaskEditViewModel, ?]]] = Publisher[Keymap[TASKEdit, TaskEditViewModel, View[TASKEdit,
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
