package me.timelytask.view

import me.timelytask.model.settings.*
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.*
import me.timelytask.view.views.{CalendarView, TaskEditView, View}

trait UIManager[RenderType] {
  def uiType: UIType
  
  def activeViewPublisher: PublisherImpl[ViewType]

  def calendarKeyMapPublisher: PublisherImpl[Keymap[CALENDAR, CalendarViewModel, View[CALENDAR, 
    CalendarViewModel, ?]]]

  def taskEditKeyMapPublisher: PublisherImpl[Keymap[TASKEdit, TaskEditViewModel, View[TASKEdit, 
    TaskEditViewModel, ?]]]

  def calendarViewModelPublisher: PublisherImpl[CalendarViewModel]

  def taskEditViewModelPublisher: PublisherImpl[TaskEditViewModel]

  val calendarView: CalendarView[RenderType]
  val taskEditView: TaskEditView[RenderType]

  def render: (RenderType, ViewType) => Unit

  def run(): Unit
}
