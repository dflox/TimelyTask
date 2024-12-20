package me.timelytask.view.keymaps

import me.timelytask.model.settings.{EventTypeId, ViewType}
import me.timelytask.view.events.Event
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.views.View

trait EventResolver[VT <: ViewType, ViewModelType <: ViewModel[VT], V <: View[VT, 
  ViewModelType, ?]] {
  def resolveAndCallEvent(eventType: EventTypeId, view: V): Option[Boolean]
}
