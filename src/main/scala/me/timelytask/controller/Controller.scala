package me.timelytask.controller

import me.timelytask.model.settings.Action
import me.timelytask.view.viewmodel.ViewModel

trait Controller extends ModelObserver {
  def handleAction(action: Action): Option[ViewModel]
}
