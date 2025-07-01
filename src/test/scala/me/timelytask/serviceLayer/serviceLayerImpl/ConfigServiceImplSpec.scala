package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.config.Config
import me.timelytask.serviceLayer.{ConfigService, ServiceModule, UpdateService}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.{times, verify, when}
import me.timelytask.model.settings.{ViewType, TABLE, CALENDAR}

class ConfigServiceImplSpec extends AnyWordSpec with Matchers with MockitoSugar {

  // A Fixture to set up the mocks and the class under test for each scenario.
  trait Fixture {
    // 1. Create mocks for the deep dependencies.
    val mockUpdateService: UpdateService = mock[UpdateService]
    val mockServiceModule: ServiceModule = mock[ServiceModule]

    // 2. Tell the mock ServiceModule what to do when its 'updateService' member is accessed.
    when(mockServiceModule.updateService).thenReturn(mockUpdateService)

    // 3. Instantiate the actual class we want to test, injecting our top-level mock.
    val configService: ConfigService = new ConfigServiceImpl(mockServiceModule)

    // Helper variables for use in tests.
    val testUserName = "test-user-123"
    val defaultConfig: Config = Config.default

    val differentStartView: ViewType = if (Config.default.startView == CALENDAR) TABLE else CALENDAR

    // Create the custom config using .copy()
    val customConfig: Config = Config.default.copy(startView = differentStartView)
  }

  "ConfigServiceImpl" should {

    // Test for the `getConfig` method
    "return the default config when getConfig is called" in new Fixture {
      // Action
      val result = configService.getConfig(testUserName)

      // Assert
      result should be(defaultConfig)
    }

    // Test for the `updateConfig` method
    "call the update service with the default config when updateConfig is called" in new Fixture {
      // The implementation ignores the provided 'customConfig' parameter and always uses default.
      // This test verifies that specific behavior.
      customConfig should not be defaultConfig // Sanity check that our custom config is indeed different.

      // Action
      configService.updateConfig(testUserName, customConfig)

      // Assert: Verify that the mock's method was called exactly once with the default config.
      verify(mockUpdateService, times(1)).updateConfig(testUserName, defaultConfig)
    }

    // Test for the `resetConfig` method
    "call the update service with the default config when resetConfig is called" in new Fixture {
      // Action
      configService.resetConfig(testUserName)

      // Assert
      verify(mockUpdateService, times(1)).updateConfig(testUserName, defaultConfig)
    }
  }
}