// src/test/scala/me/timelytask/util/extensions/hashSet/HashSetExtensionsSpec.scala

package me.timelytask.util.extensions.hashSet

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.HashSet

class HashSetExtensionsSpec extends AnyFunSuite with Matchers {

  // A simple case class for testing more complex filter logic
  case class User(id: Int, role: String)

  test("replaceOne should replace an existing element that matches the filter") {
    // Setup
    val initialSet = HashSet(10, 20, 30)

    // Action: Replace the element with the value 20
    val resultSet = initialSet.replaceOne(filter = _ == 20, newElement = 99)

    // Assert
    val expectedSet = HashSet(10, 30, 99)
    resultSet should be(expectedSet)
  }

  test("replaceOne should add the new element if no element matches the filter") {
    // Setup
    val initialSet = HashSet(10, 20, 30)

    // Action: Try to replace an element that doesn't exist
    val resultSet = initialSet.replaceOne(filter = _ == 40, newElement = 99)

    // Assert
    val expectedSet = HashSet(10, 20, 30, 99)
    resultSet should be(expectedSet)
  }

  test("replaceOne should replace multiple matching elements with a single new element") {
    // Setup
    val user1 = User(1, "ADMIN")
    val user2 = User(2, "USER")
    val user3 = User(3, "ADMIN") // A second admin
    val initialSet = HashSet(user1, user2, user3)

    val replacementUser = User(99, "SUPER_ADMIN")

    // Action: Replace all users with the role "ADMIN"
    val resultSet = initialSet.replaceOne(filter = _.role == "ADMIN", newElement = replacementUser)

    // Assert: Both admins should be gone, replaced by the single new user
    val expectedSet = HashSet(user2, replacementUser)
    resultSet should be(expectedSet)
  }

  test("replaceOne should add an element to an empty set") {
    // Setup
    val initialSet = HashSet.empty[Int]

    // Action
    val resultSet = initialSet.replaceOne(filter = _ => false, newElement = 100)

    // Assert
    resultSet should be(HashSet(100))
  }
}