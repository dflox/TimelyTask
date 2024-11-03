package controller

import model.Action
import view.viewmodel.ViewModel

trait Controller extends ModelObserver {
  def handleAction(action: Action): ViewModel
}
