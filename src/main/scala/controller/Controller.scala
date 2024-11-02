package controller

import model.Action
import view.ViewModel

trait Controller extends ModelObserver {
  def handleAction(action: Action): ViewModel
}
