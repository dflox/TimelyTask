package me.timelytask.view

import me.timelytask.model.settings.*
import me.timelytask.util.Publisher
import me.timelytask.view.views.*

trait UIManager[RenderType] {
  def shutdown(): Unit
  
  def activeViewPublisher: Publisher[ViewType]

  protected val calendarViewModule: CalendarCommonsModule
  protected val taskEditViewModule: TaskEditCommonsModule

  val calendarView: CalendarView[RenderType]
  val taskEditView: TaskEditView[RenderType]
  
  def render(renderType: RenderType, viewType: ViewType): Unit

  def run(): Unit
}
