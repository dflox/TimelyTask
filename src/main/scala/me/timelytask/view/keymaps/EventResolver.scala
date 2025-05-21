package me.timelytask.view.keymaps

import me.timelytask.model.settings.{EventTypeId, ViewType}
import me.timelytask.view.eventHandlers.EventContainer
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.views.View

trait EventResolver[VT <: ViewType, ViewModelType <: ViewModel[VT, ViewModelType]] {
  protected val eventContainer: EventContainer[VT, ViewModelType]
  def resolveAndCallEvent(eventType: EventTypeId): Option[Boolean]
}
