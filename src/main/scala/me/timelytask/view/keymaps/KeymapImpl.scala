package me.timelytask.view.keymaps

import me.timelytask.model.settings.{KeymapConfig, ViewType}
import me.timelytask.model.utility.Key
import me.timelytask.view.events.event.Event
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.views.View

class KeymapImpl[VT <: ViewType, ViewModelType <: ViewModel[VT, ViewModelType]]
(override protected val keymapConfig: KeymapConfig,
 override protected val eventResolver: EventResolver[VT, ViewModelType],
 override protected val globalEventResolver: GlobalEventResolver)
  extends Keymap[VT, ViewModelType] {
  
  override def handleKey(key: Option[Key]): Boolean = {
    if key.isEmpty then return false
    keymapConfig.mappings.get(key.get).exists(event => {
      if (!globalEventResolver.resolveAndCallEvent(event)) eventResolver.resolveAndCallEvent(event)
      else false
    })
  }
}
