package me.timelytask.view.keymaps

import me.timelytask.model.settings.{KeymapConfig, ViewType}
import me.timelytask.model.utility.Key
import me.timelytask.view.viewmodel.ViewModel
import me.timelytask.view.views.View

trait Keymap[VT <: ViewType, ViewModelType <: ViewModel[VT, ViewModelType]] {
  protected val keymapConfig: KeymapConfig
  protected val eventResolver: EventResolver[VT, ViewModelType]
  def handleKey(key: Option[Key]): Boolean
}
