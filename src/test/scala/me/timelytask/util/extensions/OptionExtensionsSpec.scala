
package me.timelytask.util.extensions

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class OptionExtensionsSpec extends AnyFunSuite with Matchers {

  // A simple case class to test a custom comparator
  case class User(id: Int, name: String)

  test("equalsWithComparison should return true when both Options are Some and the comparator matches") {
    // Setup
    val optionA = Some(10)
    val optionB = Some(10)

    // Action & Assert
    val result = optionA.equalsWithComparison(optionB, (a, b) => a == b)
    result should be(true)
  }

  test("equalsWithComparison should return false when both Options are Some but the comparator does not match") {
    // Setup
    val optionA = Some(10)
    val optionB = Some(20)

    // Action & Assert
    val result = optionA.equalsWithComparison(optionB, (a, b) => a == b)
    result should be(false)
  }

  test("equalsWithComparison should correctly use a custom comparator for complex objects") {
    // Setup: Two users with the same ID but different name casing.
    val userA = Some(User(1, "Alice"))
    val userB = Some(User(1, "ALICE"))

    // Action: Use a case-insensitive comparator.
    val result = userA.equalsWithComparison(userB, (u1, u2) => u1.id == u2.id && u1.name.equalsIgnoreCase(u2.name))

    // Assert
    result should be(true)
  }

  test("equalsWithComparison should return true when both Options are None") {
    // Setup
    val optionA: Option[Int] = None
    val optionB: Option[Int] = None

    // Action & Assert
    // The comparator is irrelevant in this case but must be provided.
    val result = optionA.equalsWithComparison(optionB, (a, b) => a == b)
    result should be(true)
  }

  test("equalsWithComparison should return false when the first Option is Some and the second is None") {
    // Setup
    val optionA = Some(10)
    val optionB: Option[Int] = None

    // Action & Assert
    val result = optionA.equalsWithComparison(optionB, (a, b) => a == b)
    result should be(false)
  }

  test("equalsWithComparison should return false when the first Option is None and the second is Some") {
    // Setup
    val optionA: Option[Int] = None
    val optionB = Some(10)

    // Action & Assert
    val result = optionA.equalsWithComparison(optionB, (a, b) => a == b)
    result should be(false)
  }
}