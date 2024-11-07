package me.timelytask.view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import me.timelytask.controller.*
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.model.utility.*
import me.timelytask.model.settings.*

class InputHandlerSpec extends AnyWordSpec with MockitoSugar {
  "The InputHandler" should {
    "parse the correct command" in {
      val keyMapManager: KeyMapManager = mock[KeyMapManager]
      when(keyMapManager.getGlobalActionKeymap).thenReturn(Map(F12 -> Exit))
      when(keyMapManager.getActiveActionKeymap).thenReturn(Map(CtrlRight -> NextDay))
      
      val returnViewModel: CalendarViewModel = mock[CalendarViewModel]
      val persistenceController: PersistenceController = mock[PersistenceController]
      when(persistenceController.handleAction(Exit)).thenReturn(returnViewModel)
      
      val calendarController: CalendarController = mock[CalendarController]
      when(calendarController.handleAction(NextDay)).thenReturn(returnViewModel)
      
      val viewModelPublisher: ViewModelPublisher = mock[ViewModelPublisher]
      val controllerMap: Map[Action, Controller] = Map(Exit -> persistenceController, NextDay -> calendarController)
      val inputHandler = new InputHandler(keyMapManager, controllerMap, viewModelPublisher)
      
      val key = F12
      inputHandler.handleInput(key)
    }
  }
}
