package me.timelytask.view

import me.timelytask.model.settings.*
import me.timelytask.util.Publisher
import me.timelytask.view.events.NextDay
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.*
import me.timelytask.view.views.{CalendarView, TaskEditView, View}

trait UIManager[RenderType] {
  def activeViewPublisher: Publisher[ViewType]
  def calendarKeyMapPublisher: Publisher[Keymap[CALENDAR, CalendarViewModel,View[CALENDAR, CalendarViewModel, ?]]]
  def taskEditKeyMapPublisher: Publisher[Keymap[TASKEdit, TaskEditViewModel, View[TASKEdit, TaskEditViewModel, ?]]]
  def calendarViewModelPublisher: Publisher[CalendarViewModel]
  def taskEditViewModelPublisher: Publisher[TaskEditViewModel]

  val calendarView: CalendarView[RenderType]
  val taskEditView: TaskEditView[RenderType]

  def render: (RenderType, ViewType) => Unit

  def run(): Unit
}
