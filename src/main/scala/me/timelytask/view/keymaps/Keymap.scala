package me.timelytask.view.keymaps

import me.timelytask.model.settings.{KeymapConfig, ViewType}
import me.timelytask.model.utility.Key
import me.timelytask.view.events.Event
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.views.View

class Keymap[VT <: ViewType, ViewModelType <: ViewModel[VT, ViewModelType], V <: View[VT,
  ViewModelType, ?]](config: KeymapConfig,
                     resolver: EventResolver[VT, ViewModelType, V]) {
  def handleKey(key: Option[Key], view: V): Boolean = {
    if key.isEmpty then return false
    config.mappings.get(key.get).flatMap { eventType =>
      resolver.resolveAndCallEvent(eventType, view)
    }.getOrElse(false)
  }
}
