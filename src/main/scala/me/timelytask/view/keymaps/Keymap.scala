package me.timelytask.view.keymaps

import me.timelytask.model.settings.{KeymapConfig, ViewType}
import me.timelytask.model.utility.Key
import me.timelytask.view.events.Event
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.views.View

class Keymap[VT <: ViewType, ViewModelType <: ViewModel[VT], V <: View[VT,
  ViewModelType, ?]](config: KeymapConfig,
                        resolver: EventResolver[VT, ViewModelType, V]) {
  def handleKey(key: Key, view: V): Boolean = {
    config.mappings.get(key).flatMap { eventType =>
      resolver.resolveEvent(eventType, view).map {
        case event: Event[Unit] => event.call(())
        case event: Event[ViewModel[VT]] => event.call(view.viewModel)
        // Add other cases for different argument types if needed
      }
    }.getOrElse(false)
  }
}
