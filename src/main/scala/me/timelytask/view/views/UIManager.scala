package me.timelytask.view.views

import me.timelytask.model.settings.*
import me.timelytask.util.Publisher
import me.timelytask.view.views.*
import me.timelytask.view.views.commonsModules.{CalendarCommonsModule, TaskEditCommonsModule}
import scalafx.stage.Stage

trait UIManager[RenderType] {
  def shutdown(afterShutdownAction: () => Unit = () => ()): Unit
  
  protected val activeViewPublisher: Publisher[ViewType]
  protected val calendarViewModule: CalendarCommonsModule
  protected val taskEditViewModule: TaskEditCommonsModule

  val calendarView: CalendarView[RenderType]
  val taskEditView: TaskEditView[RenderType]
  
  def render(renderType: RenderType, viewType: ViewType): Unit

  def stage: Option[Stage]

  def run(): Unit
}
