package me.timelytask.controller

import me.timelytask.model.Model
import me.timelytask.model.settings.Action
import me.timelytask.util.Observer
import me.timelytask.view.viewmodel.ViewModel

trait Controller extends Observer[Model] {
  def handleAction(action: Action): Option[ViewModel]
}
