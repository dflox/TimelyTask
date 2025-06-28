// src/test/scala/me/timelytask/util/extensions/ListExtensionsSpec.scala

package me.timelytask.util.extensions

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ListExtensionsSpec extends AnyFunSuite with Matchers {

  // --- Success Scenarios ---

  test("replaceOne should replace an element in the middle of the list") {
    // Setup
    val initialList = List("apple", "banana", "cherry", "date")

    // Action
    val resultList = initialList.replaceOne(_ == "cherry", "coconut")

    // Assert
    resultList should be(List("apple", "banana", "coconut", "date"))
  }

  test("replaceOne should replace the first element of the list") {
    // Setup
    val initialList = List("apple", "banana", "cherry")

    // Action
    val resultList = initialList.replaceOne(_ == "apple", "apricot")

    // Assert
    resultList should be(List("apricot", "banana", "cherry"))
  }

  test("replaceOne should replace the last element of the list") {
    // Setup
    val initialList = List("apple", "banana", "cherry")

    // Action
    val resultList = initialList.replaceOne(_ == "cherry", "coconut")

    // Assert
    resultList should be(List("apple", "banana", "coconut"))
  }

  // --- Failure Scenarios (Exception Handling) ---

  test("replaceOne should throw NoSuchElementException if no element matches") {
    // Setup
    val initialList = List(10, 20, 30)

    // Action & Assert
    val exception = intercept[NoSuchElementException] {
      initialList.replaceOne(_ == 99, 100)
    }

    exception.getMessage should be("No element matching the filter was found.")
  }

  test("replaceOne should throw NoSuchElementException for an empty list") {
    // Setup
    val initialList = List.empty[String]

    // Action & Assert
    assertThrows[NoSuchElementException] {
      initialList.replaceOne(_ => true, "new")
    }
  }

  test("replaceOne should throw IllegalArgumentException if more than one element matches") {
    // Setup
    val initialList = List("cat", "dog", "cat", "fish")

    // Action & Assert
    val exception = intercept[IllegalArgumentException] {
      initialList.replaceOne(_ == "cat", "lion")
    }

    exception.getMessage should be("More than one element matches the filter.")
  }

  test("replaceOne should throw IllegalArgumentException for multiple adjacent matches") {
    // Setup
    val initialList = List(1, 2, 2, 3)

    // Action & Assert
    assertThrows[IllegalArgumentException] {
      initialList.replaceOne(_ == 2, 99)
    }
  }
}