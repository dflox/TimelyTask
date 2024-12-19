package me.timelytask.view

import me.timelytask.model.settings.*
import me.timelytask.model.settings.ViewType._
import me.timelytask.util.Publisher
import me.timelytask.view.events.NextDay
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.*
import me.timelytask.view.views.{CalendarView, TaskEditView, View}

class UIManagerImpl[RenderType](
  val calendarView: CalendarView[RenderType],
  val taskEditView: TaskEditView[RenderType]
) extends UIManager[RenderType] {

  override val activeViewPublisher: Publisher[ViewType] = new Publisher[ViewType](None)
  override val calendarKeyMapPublisher: Publisher[Keymap[CALENDAR, CalendarViewModel, CalendarView[?]]] = new Publisher[Keymap[CALENDAR, CalendarViewModel, CalendarView[?]]](None)
  override val taskEditKeyMapPublisher: Publisher[Keymap[TASKEdit, TaskEditViewModel, TaskEditView[?]]] = new Publisher[Keymap[TASKEdit, TaskEditViewModel, TaskEditView[?]]](None)
  override val calendarViewModelPublisher: Publisher[CalendarViewModel] = new Publisher[CalendarViewModel](None)
  override val taskEditViewModelPublisher: Publisher[TaskEditViewModel] = new Publisher[TaskEditViewModel](None)

  override def render: (RenderType, ViewType) => Unit = (renderType, viewType) => {
  viewType match {
    case CALENDAR => calendarView.render(renderType, viewType)
    case TASKEdit => taskEditView.render(renderType, viewType)
  }
}

  override def run(): Unit = {
    
  }
}