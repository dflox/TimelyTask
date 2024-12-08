package me.timelytask.view.views

import me.timelytask.model.settings.ViewType
import me.timelytask.model.utility.Key
import me.timelytask.util.Publisher
import me.timelytask.view.keymaps.Keymap
import me.timelytask.view.viewmodel.ViewModel

trait View[VT <: ViewType, ViewModelType <: ViewModel[VT]](using
                                                           keymapPublisher: Publisher[Keymap[
                                                             VT,
                                                             ViewModelType,
                                                             View[VT,ViewModelType]]]) {
  var viewModel: ViewModelType
  def handleKey(key: Key): Boolean = {
    keymapPublisher.getValue.handleKey(key, this)
  }
  def update(viewModel: ViewModelType): Boolean
}
