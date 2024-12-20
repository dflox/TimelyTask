package me.timelytask

import me.timelytask.controller.commands.UndoManager
import me.timelytask.model.Model
import me.timelytask.model.settings.{CALENDAR, TASKEdit, ViewType}
import me.timelytask.util.{ApplicationThread, CancelableTask, Publisher}
import me.timelytask.view.eventHandlers.{CalendarEventHandler, GlobalEventHandler, TaskEditEventHandler}
import me.timelytask.view.keymaps.{CalendarViewResolver, Keymap, TaskEditViewResolver}
import me.timelytask.view.tui.TUIManager
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}
import me.timelytask.view.views.View

class UiInstance(using modelPublisher: Publisher[Model]) {

  given undoManager: UndoManager = new UndoManager()

  given activeViewPublisher: Publisher[ViewType] = Publisher[ViewType](Some(CALENDAR))

  given calendarViewModelPublisher: Publisher[CalendarViewModel] = Publisher[CalendarViewModel](
    None)

  given taskEditViewModelPublisher: Publisher[TaskEditViewModel] = Publisher[TaskEditViewModel](
    None)

  given calendarKeyMapPublisher: Publisher[Keymap[CALENDAR, CalendarViewModel, View[CALENDAR,
    CalendarViewModel, ?]]] = Publisher[Keymap[CALENDAR, CalendarViewModel, View[CALENDAR,
    CalendarViewModel, ?]]](None)

  given taskEditKeyMapPublisher: Publisher[Keymap[TASKEdit, TaskEditViewModel, View[TASKEdit,
    TaskEditViewModel, ?]]] = Publisher[Keymap[TASKEdit, TaskEditViewModel, View[TASKEdit,
    TaskEditViewModel, ?]]](None)

  val calendarEventHandler: CalendarEventHandler = new CalendarEventHandler()

  val taskEditEventHandler: TaskEditEventHandler = new TaskEditEventHandler()

  val globalEventHandler: GlobalEventHandler = new GlobalEventHandler()

  val uiManager: TUIManager = TUIManager()

  def run(): Unit = {
    init()
    val uiThread =  new ApplicationThread[Unit]()
    uiThread.run(uiManager.run()).await()
  }

  private def init(): Unit = {
    if modelPublisher.getValue.isEmpty then return
    calendarKeyMapPublisher.update(Some(Keymap[CALENDAR, CalendarViewModel, View[CALENDAR,
      CalendarViewModel, ?]](modelPublisher.getValue.get.config.keymaps(CALENDAR), new
        CalendarViewResolver())))

    taskEditKeyMapPublisher.update(Some(Keymap[TASKEdit, TaskEditViewModel, View[TASKEdit,
      TaskEditViewModel, ?]](modelPublisher.getValue.get.config.keymaps(TASKEdit), new
        TaskEditViewResolver())))
  }
}
