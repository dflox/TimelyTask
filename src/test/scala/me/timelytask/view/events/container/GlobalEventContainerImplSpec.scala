package me.timelytask.view.events.container

import me.timelytask.controller.{ControllerModule, CoreController, ModelController, PersistenceController}
import me.timelytask.controller.commands.CommandHandler
import me.timelytask.controller.controllersImpl.PersistenceControllerImpl
import me.timelytask.core.CoreModule
import me.timelytask.model.settings.UIType.GUI
import me.timelytask.model.settings.{CALENDAR, TABLE, TASKEdit, ViewType}
import me.timelytask.model.task.Task
import me.timelytask.util.Publisher
import me.timelytask.view.UiInstance
import me.timelytask.view.events.EventHandler
import me.timelytask.view.events.container.contailerImpl.GlobalEventContainerImpl
import me.timelytask.view.events.event.Event
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.{never, times, verify, when}
import org.mockito.ArgumentMatchers

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

class GlobalEventContainerImplSpec extends AnyWordSpec with Matchers with MockitoSugar {

  trait Fixture {
    val mockPersistenceController: PersistenceControllerImpl = mock[PersistenceControllerImpl]

    val mockCommandHandler: CommandHandler = mock[CommandHandler]
    val mockCoreController: CoreController = mock[CoreController]
    val mockModelController: ModelController = mock[ModelController]

    val mockControllerModule: ControllerModule = mock[ControllerModule]
    val mockCoreModule: CoreModule = mock[CoreModule]

    val mockEventHandler: EventHandler = mock[EventHandler]
    val mockActiveViewPublisher: Publisher[ViewType] = mock[Publisher[ViewType]]
    val mockUiInstance: UiInstance = mock[UiInstance]
    val userToken = "test-user-token"

    when(mockCoreModule.controllers).thenReturn(mockControllerModule)
    when(mockControllerModule.persistenceController).thenReturn(mockPersistenceController)
    when(mockControllerModule.commandHandler).thenReturn(mockCommandHandler)
    when(mockControllerModule.coreController).thenReturn(mockCoreController)
    when(mockControllerModule.modelController).thenReturn(mockModelController)

    val container = new GlobalEventContainerImpl(
      mockCoreModule,
      mockEventHandler,
      mockActiveViewPublisher,
      mockUiInstance,
      userToken
    )

    val eventCaptor: ArgumentCaptor[Event[Unit]] = ArgumentCaptor.forClass(classOf[Event[Unit]])

    def captureAndExecuteEvent(): Unit = {
      verify(mockEventHandler, times(1)).handle(eventCaptor.capture())
      eventCaptor.getValue.call
    }
  }

  "The GlobalEventContainerImpl" when {
    "handling undo/redo commands" should {
      "trigger an undo event" in new Fixture {
        container.undo()
        captureAndExecuteEvent()
        verify(mockCommandHandler, times(1)).undo(userToken)
      }

      "trigger a redo event" in new Fixture {
        container.redo()
        captureAndExecuteEvent()
        verify(mockCommandHandler, times(1)).redo(userToken)
      }
    }

    "handling view switching" should {
      "switch to TABLE view when not in TASKEdit" in new Fixture {
        when(mockActiveViewPublisher.getValue).thenReturn(Some(CALENDAR))
        container.switchToView()
        captureAndExecuteEvent()
        verify(mockActiveViewPublisher, times(1)).update(Some(TABLE))
      }
    }

    "handling application lifecycle" should {
      "trigger an application shutdown event" in new Fixture {
        container.shutdownApplication()
        captureAndExecuteEvent()
        verify(mockCoreController, times(1)).shutdownApplication()
      }

      "trigger a new window event" in new Fixture {
        container.newWindow()
        captureAndExecuteEvent()
        verify(mockUiInstance, times(1)).addUi(GUI)
      }

      "trigger a new instance event" in new Fixture {
        container.newInstance()
        captureAndExecuteEvent()
        verify(mockCoreController, times(1)).newGuiInstance()
      }

      "trigger a close instance event" in new Fixture {
        container.closeInstance()
        captureAndExecuteEvent()
        verify(mockCoreController, times(1)).closeInstance(mockUiInstance)
      }
    }

    "handling data and model operations" should {
      "trigger an add random task event" in new Fixture {
        container.addRandomTask()
        captureAndExecuteEvent()
        verify(mockModelController, times(1))
          .addTask(ArgumentMatchers.eq(userToken), ArgumentMatchers.any[Task]())
      }

      "trigger an export model event with default parameters" in new Fixture {
        // Mindestens den erforderlichen serializationType angeben
        container.exportModel("json")
        captureAndExecuteEvent()

        verify(mockPersistenceController, times(1))
          .saveModel(
            ArgumentMatchers.eq(userToken),
            ArgumentMatchers.eq(None),
            ArgumentMatchers.eq(None),
            ArgumentMatchers.eq("json")
          )
      }

      "trigger an export model event with custom parameters" in new Fixture {
        val folderPath = Some("/path/to/folder")
        val fileName = Some("backup")

        container.exportModel("xml", folderPath, fileName)
        captureAndExecuteEvent()

        verify(mockPersistenceController, times(1))
          .saveModel(
            ArgumentMatchers.eq(userToken),
            ArgumentMatchers.eq(folderPath),
            ArgumentMatchers.eq(fileName),
            ArgumentMatchers.eq("xml")
          )
      }
    }
  }
}