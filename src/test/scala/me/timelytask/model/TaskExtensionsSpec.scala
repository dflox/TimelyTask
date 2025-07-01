// src/test/scala/me/timelytask/model/task/TaskExtensionsSpec.scala

package me.timelytask.model.task

import com.github.nscala_time.time.Imports.{DateTime, Period}
import me.timelytask.model.deadline.Deadline
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

import java.util.UUID
import scala.collection.immutable.HashSet

class TaskExtensionsSpec extends AnyWordSpec with Matchers with MockitoSugar {

  // A Fixture to provide a clean, predictable starting Task for each test.
  trait Fixture {
    // We use Task.exampleTask as a template to ensure our test task is always consistent.
    val initialTask: Task = Task.exampleTask.copy(
      uuid = UUID.fromString("00000000-0000-0000-0000-000000000001"),
      tags = HashSet.empty,
      dependentOn = HashSet.empty
    )
  }

  "Task Extensions" should {

    // --- Testing the `withName` method ---
    "withName" should {
      "update the task with a new valid name" in new Fixture {
        val newName = "A valid new name"
        val updatedTask = initialTask.withName(newName)

        updatedTask.name should be(newName)
        updatedTask.description should be(initialTask.description) // Assert other fields are unchanged
      }

      "throw an IllegalArgumentException for an empty name" in new Fixture {
        val exception = intercept[IllegalArgumentException] {
          initialTask.withName("")
        }
        exception.getMessage should be("The task name cannot be empty.")
      }
    }

    // --- Testing simple setters ---
    "withDescription should update the description" in new Fixture {
      val newDescription = "A new description."
      val updatedTask = initialTask.withDescription(newDescription)

      updatedTask.description should be(newDescription)
      updatedTask.name should be(initialTask.name)
    }

    "withPriority should update the priority" in new Fixture {
      val newPriority = Some(UUID.randomUUID())
      val updatedTask = initialTask.withPriority(newPriority)

      updatedTask.priority should be(newPriority)
    }

    // --- Testing Set manipulation ---
    "addTag should add a new tag to the set" in new Fixture {
      val tagToAdd = UUID.randomUUID()
      val updatedTask = initialTask.addTag(tagToAdd)

      updatedTask.tags should contain(tagToAdd)
      updatedTask.tags.size should be(1)
    }

    "removeTag should remove an existing tag from the set" in new Fixture {
      val tag1 = UUID.randomUUID()
      val tag2 = UUID.randomUUID()
      val taskWithTags = initialTask.copy(tags = HashSet(tag1, tag2))

      val updatedTask = taskWithTags.removeTag(tag1)

      updatedTask.tags should not contain (tag1)
      updatedTask.tags should contain(tag2)
      updatedTask.tags.size should be(1)
    }

    "removeAllTags should clear all tags" in new Fixture {
      val taskWithTags = initialTask.copy(tags = HashSet(UUID.randomUUID(), UUID.randomUUID()))
      taskWithTags.tags.isEmpty should be(false) // Sanity check

      val updatedTask = taskWithTags.removeAllTags()

      updatedTask.tags should be(empty)
    }

    "withTags should replace the entire set of tags" in new Fixture {
      val initialTags = HashSet(UUID.randomUUID())
      val newTags = HashSet(UUID.randomUUID(), UUID.randomUUID())
      val taskWithInitialTags = initialTask.copy(tags = initialTags)

      val updatedTask = taskWithInitialTags.withTags(newTags)

      updatedTask.tags should be(newTags)
    }

    // --- Testing a more complex object field ---
    "withDeadline should update the deadline" in new Fixture {
      val newDeadline = mock[Deadline] // Mocking is easier than constructing a real one
      val updatedTask = initialTask.withDeadline(newDeadline)

      updatedTask.deadline should be(newDeadline)
    }

    // --- Testing another set manipulation ---
    "addDependentOn should add a new dependency" in new Fixture {
      val dependentUuid = UUID.randomUUID()
      val updatedTask = initialTask.addDependentOn(dependentUuid)

      updatedTask.dependentOn should contain(dependentUuid)
    }

    "removeDependentOn should remove an existing dependency" in new Fixture {
      val dependentUuid = UUID.randomUUID()
      val taskWithDependency = initialTask.copy(dependentOn = HashSet(dependentUuid))

      val updatedTask = taskWithDependency.removeDependentOn(dependentUuid)

      updatedTask.dependentOn should be(empty)
    }
  }
}