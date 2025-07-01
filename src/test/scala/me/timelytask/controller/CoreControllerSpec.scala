//package me.timelytask.controller
//
//import me.timelytask.controller.commands.{CommandHandler, IrreversibleCommand}
//import me.timelytask.controller.controllersImpl.CoreControllerImpl
//import me.timelytask.controller.{PersistenceController, UpdateController}
//import me.timelytask.core.{CoreModule, StartUpConfig, UiInstanceConfig}
//import me.timelytask.util.CancelableFuture
//import me.timelytask.view.UiInstance
//import org.mockito.ArgumentMatchers.any
//import org.mockito.Mockito.*
//import org.mockito.{ArgumentCaptor, ArgumentMatchers}
//import org.scalatest.BeforeAndAfterEach
//import org.scalatest.matchers.should.Matchers
//import org.scalatest.wordspec.AnyWordSpec
//import org.scalatestplus.mockito.MockitoSugar
//
//
//class CoreControllerSpec extends AnyWordSpec with Matchers with MockitoSugar with BeforeAndAfterEach {
//
//  // --- Mocks for all dependencies ---
//  private var mockCommandHandler: CommandHandler = _
//  private var mockCancelableFuture: CancelableFuture[Unit] = _
//  private var mockPersistenceController: PersistenceController = _
//  private var mockUpdateController: UpdateController = _
//  private var mockCoreModule: CoreModule = _
//
//  // --- The class under test ---
//  private var controller: CoreControllerImpl = _
//
//  // --- Setup method to create fresh mocks before each test ---
//  override def beforeEach(): Unit = {
//    mockCommandHandler = mock[CommandHandler]
//    mockCancelableFuture = mock[CancelableFuture[Unit]]
//    when(mockCommandHandler.runner).thenReturn(mockCancelableFuture)
//
//    // Mock other direct dependencies
//    mockPersistenceController = mock[PersistenceController]
//    mockUpdateController = mock[UpdateController]
//    mockCoreModule = mock[CoreModule]
//
//    // Instantiate the controller with mocks
//    controller = new CoreControllerImpl(
//      mockCommandHandler,
//      mockPersistenceController,
//      mockUpdateController,
//      mockCoreModule
//    )
//  }
//
//  "A CoreControllerImpl" should {
//
//    "startUpApplication" should {
//      "throw an exception if the startup config is missing" in {
//        // Action & Assertion
//        val exception = an[Exception] should be thrownBy {
//          controller.startUpApplication(None)
//        }
//        an[Exception] should be thrownBy {
//          controller.startUpApplication(None)
//        }
//      }
//
//      "do nothing if the application is already running" in {
//        // Use reflection to set the private runningFlag to true
//        val runningFlagField = classOf[CoreControllerImpl].getDeclaredField("runningFlag")
//        runningFlagField.setAccessible(true)
//        runningFlagField.set(controller, true)
//
//        // Action
//        controller.startUpApplication(Some(StartUpConfig(List.empty)))
//
//        // Assert that no initialization or other startup methods were called
//        verify(mockUpdateController, never()).init()
//        verify(mockCancelableFuture, never()).await()
//      }
//    }
//
////    "shutdownApplication" should {
////      "execute a shutdown command to stop the application" in {
////
////        val commandCaptor: ArgumentCaptor[IrreversibleCommand[Unit]] =
////          ArgumentCaptor.forClass(classOf[IrreversibleCommand[Unit]])
////
////        // Setup: Add a mock UI instance to the internal list using reflection
////        val mockUiInstance = mock[UiInstance]
////        val uiInstancesField = classOf[CoreControllerImpl].getDeclaredField("uiInstances")
////        uiInstancesField.setAccessible(true)
////        uiInstancesField.set(controller, Vector(mockUiInstance))
////
////        // Action
////        controller.shutdownApplication()
////
////        // Assertions
////        verify(mockCommandHandler).handle(commandCaptor.capture())
////        val capturedCommand = commandCaptor.getValue
////        capturedCommand.execute
////
////        verify(mockUiInstance, times(1)).shutdown()
////        verify(mockCancelableFuture, times(1)).cancel()
////
////        val runningFlagField = classOf[CoreControllerImpl].getDeclaredField("runningFlag")
////        runningFlagField.setAccessible(true)
////        val flagValue = runningFlagField.get(controller).asInstanceOf[Boolean]
////        flagValue should be(false)
////      }
////    }
//
//    "closeInstance" should {
//      "shut down the specific instance if other instances remain" in {
//
//        val spiedController = spy(controller)
//
//        // Setup
//        val instanceToClose = mock[UiInstance]
//        val instanceToKeep = mock[UiInstance]
//        val uiInstancesField = classOf[CoreControllerImpl].getDeclaredField("uiInstances")
//        uiInstancesField.setAccessible(true)
//        uiInstancesField.set(spiedController, Vector(instanceToClose, instanceToKeep))
//
//        // Action
//        spiedController.closeInstance(instanceToClose)
//
//        // Assertions
//        verify(instanceToClose, times(1)).shutdown()
//        verify(instanceToKeep, never()).shutdown()
//        verify(spiedController, never()).shutdownApplication()
//
//        // Verify the internal list was updated correctly
//        val remainingInstances = uiInstancesField.get(spiedController).asInstanceOf[Vector[UiInstance]]
//        remainingInstances should contain only instanceToKeep
//      }
//
//      "shut down the entire application if it's the last instance" in {
//
//        val spiedController = spy(controller)
//        // We need to prevent the real shutdownApplication from running recursively
//        doNothing().when(spiedController).shutdownApplication()
//
//        // Setup
//        val lastInstance = mock[UiInstance]
//        val uiInstancesField = classOf[CoreControllerImpl].getDeclaredField("uiInstances")
//        uiInstancesField.setAccessible(true)
//        uiInstancesField.set(spiedController, Vector(lastInstance))
//
//        // Action
//        spiedController.closeInstance(lastInstance)
//
//        // Assertions
//        verify(spiedController, times(1)).shutdownApplication()
//
//        // Verify the internal list is now empty
//        val remainingInstances = uiInstancesField.get(spiedController).asInstanceOf[Vector[UiInstance]]
//        remainingInstances should be(empty)
//      }
//    }
//  }
//}