package me.timelytask.view

import me.timelytask.controller.*
import me.timelytask.model.settings.{Action, Exit, GoToToday, NextDay, NextWeek, NoAction, PreviousDay, PreviousWeek}
import me.timelytask.model.utility.Keyboard
import me.timelytask.view.viewmodel.ViewModel

class InputHandler(keyMapManager: KeyMapManager, controllerMap: Map[Action, Controller], viewModelPublisher: ViewModelPublisher) {
  
  def handleInput(key: Keyboard): ViewModel = {
    val action: Action = keyMapManager.getActiveActionKeymap.getOrElse(key,
      keyMapManager.getGlobalActionKeymap.getOrElse(key, NoAction))
    val viewModel = controllerMap.get(action).orElse(controllerMap.get(action)) match {
      case Some(controller) => controller.handleAction(action)
      case None => viewModelPublisher.getCurrentViewModel
    }
    viewModelPublisher.updateViewModel(viewModel)
    viewModel
  }
}

object InputHandler {
  def getControllerMap(calendarController: CalendarController, persistenceController: PersistenceController): Map[Action, Controller] = Map(
    NextDay -> calendarController,
    PreviousDay -> calendarController,
    NextWeek -> calendarController,
    PreviousWeek -> calendarController,
    GoToToday -> calendarController,
    Exit -> persistenceController
  )
}