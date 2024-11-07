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
import org.scalatest.matchers.should.Matchers.shouldEqual

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

      val key1 = F12
      val key2 = CtrlRight
      inputHandler.handleInput(key1)
      val actionCaptor = ArgumentCaptor.forClass(classOf[Action])
      verify(persistenceController).handleAction(actionCaptor.capture())
      actionCaptor.getValue shouldEqual Exit

      inputHandler.handleInput(key2)
      verify(calendarController).handleAction(actionCaptor.capture())
      actionCaptor.getValue shouldEqual NextDay

      val key3 = A
      inputHandler.handleInput(key3)
      verify(viewModelPublisher).getCurrentViewModel
    }

    "create controller map" in {
      val persistenceController = mock[PersistenceController]
      val calendarController: CalendarController = mock[CalendarController]
      val valueSetControllerMap = InputHandler
        .getControllerMap(calendarController, persistenceController)
        .values.toSet
      
      valueSetControllerMap.contains(persistenceController) shouldEqual true
      valueSetControllerMap.contains(calendarController) shouldEqual true
    }
  }
}
