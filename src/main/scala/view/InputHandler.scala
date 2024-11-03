package view

import controller.*
import model.*

class InputHandler(keyMapManager: KeyMapManager, controllerMap: Map[Action, Controller], viewModelPublisher: ViewModelPublisher) {
  
  def handleInput(input: Int): ViewModel = {
    val viewAction: Action = keyMapManager.getActiveKeymap.getBound(input.toChar.toString)
    val globalAction: Action = keyMapManager.getGlobalKeymap.getBound(input.toChar.toString)
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