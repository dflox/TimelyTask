package me.timelytask.serviceLayer.servicelayerImpl

import me.timelytask.model.user.User
import me.timelytask.repository.UserRepository
import me.timelytask.serviceLayer.{ServiceModule, UserService}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.{times, verify, when}
import org.mockito.ArgumentMatchers.{any, eq as eqTo}

class UserServiceImplSpec extends AnyWordSpec with Matchers with MockitoSugar {

  // A Fixture to set up the mocks and the service under test for each scenario.
  trait Fixture {
    // 1. Mock the dependencies passed to the constructor.
    // We don't need to mock ServiceModule since it's not used in this specific implementation.
    val mockUserRepository: UserRepository = mock[UserRepository]
    val mockServiceModule: ServiceModule = mock[ServiceModule]

    // 2. Instantiate the actual class we want to test, injecting our mock repository.
    val userService: UserService = new UserServiceImpl(mockServiceModule, mockUserRepository)

    // 3. Helper variables for use in tests.
    val testUserName = "test-user"
    val newUserName = "new-user-name"
  }

  "UserServiceImpl" should {

    "userExists should delegate to the repository and return its result" in new Fixture {
      // Arrange: Program the mock repository to return 'true'.
      when(mockUserRepository.userExists(testUserName)).thenReturn(true)

      // Action
      val result = userService.userExists(testUserName)

      // Assert: Check that the result from the service matches the result from the mock.
      result should be(true)
      // Verify that the repository method was called.
      verify(mockUserRepository, times(1)).userExists(testUserName)
    }

    "getUser should delegate to the repository and return the user" in new Fixture {
      // Arrange
      val expectedUser = User(testUserName)
      when(mockUserRepository.getUser(testUserName)).thenReturn(expectedUser)

      // Action
      val result = userService.getUser(testUserName)

      // Assert
      result should be(expectedUser)
      verify(mockUserRepository, times(1)).getUser(testUserName)
    }

    "addUser should create a User object and pass it to the repository" in new Fixture {
      // This is the most important test as it verifies the internal logic.
      val expectedUserToBeAdded = User(testUserName)

      // Action
      userService.addUser(testUserName)

      // Assert: Verify that the repository was called with a User object
      // that is equal to the one we expect to have been created.
      verify(mockUserRepository, times(1)).addUser(eqTo(expectedUserToBeAdded))
    }

    "removeUser should delegate the user name to the repository" in new Fixture {
      // Action
      userService.removeUser(testUserName)

      // Assert
      verify(mockUserRepository, times(1)).removeUser(testUserName)
    }



    "updateName should delegate the old and new names to the repository" in new Fixture {
      // Action
      userService.updateName(testUserName, newUserName)

      // Assert
      verify(mockUserRepository, times(1)).updateName(testUserName, newUserName)
    }
  }
}