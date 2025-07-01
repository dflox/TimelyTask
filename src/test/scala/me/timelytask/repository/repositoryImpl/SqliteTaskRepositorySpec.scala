//package me.timelytask.repository.repositoryImpl
//
//import me.timelytask.model.task.Task
//import org.scalatest.wordspec.AnyWordSpec
//import org.scalatest.matchers.should.Matchers
//import org.scalatest.{BeforeAndAfterEach, OptionValues}
//import simplesql.{*, given}
//
//import java.sql.DriverManager
//import java.util.UUID
//import scala.collection.immutable.HashSet
//
//class SqliteTaskRepositorySpec extends AnyWordSpec with Matchers with BeforeAndAfterEach with OptionValues {
//
//  trait Fixture {
//    val ds = new DataSource(() => new simplesql.Connection(DriverManager.getConnection("jdbc:sqlite::memory:")))
//    val repo = new SqliteTaskRepository(ds)
//    val userName = "testUser"
//    val otherUserName = "anotherUser"
//
//    val tagId1: UUID = UUID.randomUUID()
//    val tagId2: UUID = UUID.randomUUID()
//
//    val dependencyId1: UUID = UUID.randomUUID()
//    val dependencyId2: UUID = UUID.randomUUID()
//
//    // Task with NO tags or dependencies for simple tests
//    val simpleTask: Task = Task.exampleTask.copy(
//      uuid = UUID.randomUUID(),
//      name = "Simple Task",
//      tags = HashSet.empty,
//      dependentOn = HashSet.empty
//    )
//
//    // A task that can BE a dependency
//    val dependencyTask1: Task = Task.exampleTask.copy(uuid = dependencyId1, name = "Dependency 1")
//
//    // A task with existing tags and dependencies for update/delete tests
//    val complexTask: Task = Task.exampleTask.copy(
//      uuid = UUID.randomUUID(),
//      name = "Complex Task",
//      tags = HashSet(tagId1),
//      dependentOn = HashSet(dependencyId1)
//    )
//  }
//
//  // Setup and teardown remain the same, they are correct.
//  // In SqliteTaskRepositorySpec.scala
//
//  override def beforeEach(): Unit = {
//    val fixture = new Fixture {}
//    import fixture.ds
//
//    ds.transaction {
//      // Create tables required for the test setup
//      sql"CREATE TABLE users(name TEXT PRIMARY KEY)".write()
//      sql"""
//        CREATE TABLE IF NOT EXISTS tasks(
//          userid TEXT, id TEXT NOT NULL, name TEXT NOT NULL, description TEXT,
//          priority TEXT, deadline_date TEXT, deadline_initialDate TEXT,
//          deadline_completionDate TEXT, scheduleDate TEXT, state TEXT,
//          tedDuration TEXT, reoccurring BOOLEAN, recurrenceInterval TEXT,
//          realDuration TEXT,
//          PRIMARY KEY (userid, id),
//          FOREIGN KEY (userid) REFERENCES users(name) ON UPDATE CASCADE ON DELETE CASCADE
//        )
//      """.write()
//
//      // Insert users
//      sql"INSERT INTO users(name) VALUES (${fixture.userName})".write()
//      sql"INSERT INTO users(name) VALUES (${fixture.otherUserName})".write()
//
//      // Now, insert the dependency task using the robust, type-safe pattern.
//      val dependencyTask = fixture.dependencyTask1
//
//      // THE FIX: Use .map(_.toString).getOrElse("") for all optional fields
//      // to ensure the expression inside ${...} is always a String.
//      sql"""
//        INSERT INTO tasks(userid, id, name, description, priority, deadline_date,
//        deadline_initialDate, deadline_completionDate, scheduleDate, state, tedDuration, reoccurring,
//        recurrenceInterval, realDuration)
//        VALUES(
//          ${fixture.userName},
//          ${dependencyTask.uuid.toString},
//          ${dependencyTask.name},
//          ${dependencyTask.description},
//          ${dependencyTask.priority.map(_.toString).getOrElse("")},
//          ${dependencyTask.deadline.date.toString},
//          ${dependencyTask.deadline.initialDate.map(_.toString).getOrElse("")},
//          ${dependencyTask.deadline.completionDate.map(_.toString).getOrElse("")},
//          ${dependencyTask.scheduleDate.toString},
//          ${dependencyTask.state.map(_.toString).getOrElse("")},
//          ${dependencyTask.tedDuration.toString},
//          ${dependencyTask.reoccurring},
//          ${dependencyTask.recurrenceInterval.toString},
//          ${dependencyTask.realDuration.map(_.toString).getOrElse("")}
//        )
//      """.write()
//    }
//  }
//
//  override def afterEach(): Unit = {
//    val fixture = new Fixture {}
//    import fixture.ds
//    ds.transaction {
//      sql"DROP TABLE IF EXISTS task_dependencies".write()
//      sql"DROP TABLE IF EXISTS task_tags".write()
//      sql"DROP TABLE IF EXISTS tasks".write()
//      sql"DROP TABLE IF EXISTS users".write()
//    }
//  }
//
//  "SqliteTaskRepository" should {
//
//    "add a simple task and retrieve it by ID" in new Fixture {
//      // Action: Add a task with no tags or dependencies. This avoids problematic code paths.
//      repo.addTask(userName, simpleTask)
//      val retrievedTask = repo.getTaskById(userName, simpleTask.uuid)
//
//      retrievedTask.name.shouldBe(simpleTask.name)
//      retrievedTask.tags.shouldBe(empty)
//      retrievedTask.dependentOn.shouldBe(empty)
//    }
//
//    "add a complex task and retrieve its relations" in new Fixture {
//      // Action: Add a task that has tags and dependencies
//      repo.addTask(userName, complexTask)
//      val retrievedTask = repo.getTaskById(userName, complexTask.uuid)
//
//      // Assert
//      retrievedTask.name.shouldBe(complexTask.name)
//      retrievedTask.tags.should(contain only tagId1)
//      retrievedTask.dependentOn.should(contain only dependencyId1)
//    }
//
//    "return all tasks for a specific user" in new Fixture {
//      repo.addTask(userName, simpleTask)
//      repo.addTask(userName, complexTask)
//
//      val allTasks = repo.getAllTasks(userName)
//
//      allTasks.map(_.uuid).should(contain theSameElementsAs Seq(simpleTask.uuid, complexTask.uuid))
//      allTasks.size.shouldBe(2)
//    }
//
//    "delete a task and its relations" in new Fixture {
//      // Arrange
//      repo.addTask(userName, complexTask)
//
//      // Action
//      repo.deleteTask(userName, complexTask.uuid)
//
//      // Assert
//      assertThrows[Exception] {
//        repo.getTaskById(userName, complexTask.uuid)
//      }
//
//      // Check that relational data is also gone
//      val tagsCount = ds.transaction(sql"SELECT COUNT(*) FROM task_tags WHERE taskId = ${complexTask.uuid.toString}".readOne[Int])
//      val depsCount = ds.transaction(sql"SELECT COUNT(*) FROM task_dependencies WHERE taskId = ${complexTask.uuid.toString}".readOne[Int])
//
//      tagsCount.shouldBe(0)
//      depsCount.shouldBe(0)
//    }
//
//    "update a task's fields, tags, and dependencies" in new Fixture {
//      // Arrange: Start with a complex task already in the DB
//      repo.addTask(userName, complexTask)
//
//      // Create an updated version
//      val updatedTask = complexTask.copy(
//        name = "Updated Name",
//        tags = HashSet(tagId2), // Change tag from tagId1 to tagId2
//        dependentOn = HashSet.empty // Remove all dependencies
//      )
//
//      // Action
//      repo.updateTask(userName, complexTask.uuid, updatedTask)
//      val retrievedTask = repo.getTaskById(userName, complexTask.uuid)
//
//      // Assert
//      retrievedTask.name.shouldBe("Updated Name")
//      retrievedTask.tags.should(contain only tagId2)
//      retrievedTask.tags.shouldNot(contain(tagId1))
//      retrievedTask.dependentOn.shouldBe(empty)
//    }
//
//    "delete all tasks for a user but not for others" in new Fixture {
//      // Arrange
//      repo.addTask(userName, simpleTask)
//      repo.addTask(otherUserName, complexTask) // Task for another user
//
//      // Action
//      repo.deleteAllTasks(userName)
//
//      // Assert
//      repo.getAllTasks(userName).shouldBe(empty)
//      noException should be thrownBy repo.getTaskById(otherUserName, complexTask.uuid)
//    }
//
//    "return an empty list when a user has no tasks" in new Fixture {
//      repo.getAllTasks(userName).shouldBe(empty)
//    }
//  }
//}