package me.timelytask.view

import me.timelytask.model.settings.*
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.eventHandlers.{CalendarEventContainer, TaskEditEventContainer}
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.*
import me.timelytask.view.views.*

trait UIManager[RenderType] {
  def shutdown(): Unit
  
  def activeViewPublisher: Publisher[ViewType]

  protected val calendarViewModule: CalendarCommonsModule
  protected val taskEditViewModule: TaskEditCommonsModule

  val calendarView: CalendarView[RenderType]
  val taskEditView: TaskEditView[RenderType]
  
  def render: (RenderType, ViewType) => Unit

  def run(): Unit
}
