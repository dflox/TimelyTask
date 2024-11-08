package me.timelytask.view

import me.timelytask.controller.*
import me.timelytask.model.settings.*
import me.timelytask.model.utility.Keyboard
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.ViewModel

class InputHandler(keyMapManager: KeyMapManager, controllerMap: Map[Action, Controller], viewModelPublisher: Publisher[ViewModel]) {
  
  def handleInput(key: Keyboard): Option[ViewModel] = {
    val action: Action = keyMapManager.getActiveActionKeymap.getOrElse(key,
      keyMapManager.getGlobalActionKeymap.getOrElse(key, NoAction))
    val viewModel = controllerMap.get(action).orElse(controllerMap.get(action)) match {
      case Some(controller) => controller.handleAction(action)
      case None => None
    }
    if viewModel.isDefined then
      viewModelPublisher.update(viewModel.get)
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
    ShowWholeWeek -> calendarController,
    ShowMoreDays -> calendarController,
    ShowLessDays -> calendarController,
    Exit -> persistenceController,
  )
}