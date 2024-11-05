package view

import controller.*
import model.*
import model.utility.*
import view.viewmodel.ViewModel

class InputHandler(keyMapManager: KeyMapManager, controllerMap: Map[Action, Controller], viewModelPublisher: ViewModelPublisher) {
  
  def handleInput(key: Keyboard): ViewModel = {
    val viewAction: Action = keyMapManager.getActiveActionKeymap.getOrElse(key, NoAction)
    val globalAction: Action = keyMapManager.getGlobalActionKeymap.getOrElse(key, NoAction)
    val viewModel = controllerMap.get(viewAction).orElse(controllerMap.get(globalAction)) match {
      case Some(controller) => controller.handleAction(viewAction)
      case None => viewModelPublisher.getCurrentViewModel
    }
    viewModelPublisher.updateViewModel(viewModel)
    viewModel
  }
}

object InputHandler {
  def getControllerMap(calendarController: CalendarController): Map[Action, Controller] = Map(
    NextDay -> calendarController,
    PreviousDay -> calendarController,
    NextWeek -> calendarController,
    PreviousWeek -> calendarController,
    GoToToday -> calendarController
  )
}