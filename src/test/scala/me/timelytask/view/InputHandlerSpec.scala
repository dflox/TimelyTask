package me.timelytask.view

import me.timelytask.controller.*
import me.timelytask.model.settings.*
import me.timelytask.model.utility.*
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.scalatest.matchers.should.Matchers.shouldEqual
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

class InputHandlerSpec extends AnyWordSpec
                       with MockitoSugar {
  "The InputHandler" should {
    "parse the correct command" in {
      val keyMapManager: KeyMapManager = mock[KeyMapManager]
      when(keyMapManager.getGlobalActionKeymap).thenReturn(Map(F12 -> Exit))
      when(keyMapManager.getActiveActionKeymap).thenReturn(Map(CtrlRight -> NextDay))

      val returnViewModel: CalendarViewModel = mock[CalendarViewModel]
      val persistenceController: PersistenceController = mock[PersistenceController]
      when(persistenceController.handleAction(Exit)).thenReturn(None)

      val calendarController: CalendarController = mock[CalendarController]
      when(calendarController.handleAction(NextDay)).thenReturn(Some(returnViewModel))

      val viewModelPublisher = mock[Publisher[ViewModel]]
      val controllerMap: Map[Action, Controller] = Map(Exit -> persistenceController,
        NextDay -> calendarController)
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
      inputHandler.handleInput(key3).shouldEqual(None)
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
