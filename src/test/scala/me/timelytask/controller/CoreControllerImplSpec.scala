package me.timelytask.controller

// Annahme: CancelableFuture ist hier definiert. Passe den Import ggf. an.
import me.timelytask.controller.commands.{CommandHandler, UndoableCommand}
import me.timelytask.controller.controllersImpl.CoreControllerImpl
import me.timelytask.core.{CoreModule, StartUpConfig, UiInstanceConfig}
import me.timelytask.model.settings.CALENDAR
import me.timelytask.model.settings.UIType.GUI
import me.timelytask.util.CancelableFuture
import me.timelytask.view.UiInstance
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{never, times, verify, when}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatestplus.mockito.MockitoSugar

class CoreControllerSpec extends AnyWordSpec with Matchers with MockitoSugar {

  trait Fixture {
    val mockCommandHandler: CommandHandler = mock[CommandHandler]
    val mockPersistenceController: PersistenceController = mock[PersistenceController]
    val mockCoreModule: CoreModule = mock[CoreModule]
    val mockCancelableFuture: CancelableFuture[Any] = mock[CancelableFuture[Any]]

    when(mockCommandHandler.runner).thenReturn(mockCancelableFuture)

    val coreController = new CoreControllerImpl(
      mockCommandHandler,
      mockPersistenceController,
      mockCoreModule
    )
  }

  "The CoreController" when {
    "handling application startup" should {

      "successfully start the application with a valid config" in new Fixture {
        val startupConfig = StartUpConfig(List(UiInstanceConfig(List(GUI), CALENDAR)))

        coreController.startUpApplication(Some(startupConfig))

        verify(mockPersistenceController, times(1)).init()
        verify(mockPersistenceController, times(1)).loadModelFromDB()

        verify(mockCancelableFuture, times(1)).await()
      }

      "not start the application twice" in new Fixture {
        val startupConfig = StartUpConfig(List(UiInstanceConfig(List(GUI), CALENDAR)))

        coreController.startUpApplication(Some(startupConfig))
        coreController.startUpApplication(Some(startupConfig))

        verify(mockPersistenceController, times(1)).init()
        verify(mockPersistenceController, times(1)).loadModelFromDB()

        verify(mockCancelableFuture, times(1)).await()
      }

      "throw an exception if the startup config is missing" in new Fixture {
        val exception = intercept[Exception] {
          coreController.startUpApplication(None)
        }

        exception.getMessage shouldBe "UI startup failed because the config is missing"
        verify(mockPersistenceController, never()).init()
        verify(mockPersistenceController, never()).loadModelFromDB()
      }
    }

    "handling application shutdown" should {

      "handle a shutdown command" in new Fixture {
        coreController.shutdownApplication()
        verify(mockCommandHandler, times(1)).handle(any[UndoableCommand[Unit]])
      }
    }

    "managing UI instances" should {

      "close a specific instance if it is not the last one" in new Fixture {
        val mockUiInstance1 = mock[UiInstance]
        val mockUiInstance2 = mock[UiInstance]

        setPrivateField(coreController, "uiInstances", Vector(mockUiInstance1, mockUiInstance2))

        coreController.closeInstance(mockUiInstance1)

        verify(mockUiInstance1, times(1)).shutdown()
        verify(mockUiInstance2, never()).shutdown()
        verify(mockCommandHandler, never()).handle(any[UndoableCommand[Unit]])
      }

      "shut down the application when the last instance is closed" in new Fixture {
        val mockUiInstance = mock[UiInstance]
        setPrivateField(coreController, "uiInstances", Vector(mockUiInstance))

        coreController.closeInstance(mockUiInstance)

        verify(mockCommandHandler, times(1)).handle(any[UndoableCommand[Unit]])
        verify(mockUiInstance, never()).shutdown()
      }
    }
  }

  /**
   * Hilfsmethode um privateField für Testzwecke zu setzen.
   */
  private def setPrivateField(target: AnyRef, fieldName: String, value: Any): Unit = {
    val field = target.getClass.getDeclaredField(fieldName)
    field.setAccessible(true)
    field.set(target, value)
  }
}