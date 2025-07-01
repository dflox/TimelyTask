//// src/test/scala/me/timelytask/repository/simpleReaders/TaskSimpleReaderSpec.scala
//
//package me.timelytask.repository.simpleReaders
//
//import com.github.nscala_time.time.Imports.{DateTime, Period}
//import me.timelytask.model.deadline.Deadline
//import me.timelytask.model.task.Task
//import org.scalatest.wordspec.AnyWordSpec
//import org.scalatest.matchers.should.Matchers
//import org.scalatestplus.mockito.MockitoSugar
//import org.mockito.Mockito.when
//import simplesql.SimpleReader
//
//import java.sql.ResultSet
//import java.util.UUID
//
//class TaskSimpleReaderSpec extends AnyWordSpec with Matchers with MockitoSugar {
//
//  // A Fixture to set up mock data and the mock ResultSet.
//  trait Fixture {
//    // We will refer to the given instance directly in the tests.
//    // No need to assign it to a variable here.
//
//    // Create mock data that we will "put into" our mock ResultSet.
//    val testUuid: UUID = UUID.randomUUID()
//    val testPriorityUuid: UUID = UUID.randomUUID()
//    val testStateUuid: UUID = UUID.randomUUID()
//    val testDeadlineDate: DateTime = DateTime.now().plusDays(10)
//    val testInitialDate: DateTime = DateTime.now().plusDays(5)
//    val testCompletionDate: DateTime = DateTime.now().plusDays(15)
//    val testScheduleDate: DateTime = DateTime.now()
//    val testTedDuration: Period = Period.hours(2)
//    val testRecurrenceInterval: Period = Period.weeks(1)
//    val testRealDuration: Period = Period.minutes(90)
//    val testName = "Test Task Name"
//    val testDescription = "A description for the test."
//
//    // Create the mock ResultSet itself.
//    val mockResultSet: ResultSet = mock[ResultSet]
//  }
//
//  "A SimpleReader for Task" should {
//
//    "correctly read a ResultSet with all fields present" in new Fixture {
//      // Arrange: Program the mock ResultSet to return our test data for each column name.
//      when(mockResultSet.getString("name")).thenReturn(testName)
//      when(mockResultSet.getString("description")).thenReturn(testDescription)
//      when(mockResultSet.getObject("id", classOf[UUID])).thenReturn(testUuid)
//      when(mockResultSet.getObject("priority", classOf[UUID])).thenReturn(testPriorityUuid)
//      when(mockResultSet.getObject("deadline_date", classOf[DateTime])).thenReturn(testDeadlineDate)
//      when(mockResultSet.getObject("deadline_initialDate", classOf[DateTime])).thenReturn(testInitialDate)
//      when(mockResultSet.getObject("deadline_completionDate", classOf[DateTime])).thenReturn(testCompletionDate)
//      when(mockResultSet.getString("scheduleDate")).thenReturn(testScheduleDate.toString)
//      when(mockResultSet.getObject("state", classOf[UUID])).thenReturn(testStateUuid)
//      when(mockResultSet.getString("tedDuration")).thenReturn(testTedDuration.toString)
//      when(mockResultSet.getBoolean("reoccuring")).thenReturn(true)
//      when(mockResultSet.getObject("recurrenceInterval", classOf[Period])).thenReturn(testRecurrenceInterval)
//      when(mockResultSet.getObject("realDuration", classOf[Period])).thenReturn(testRealDuration)
//
//      val expectedTask = Task(
//        name = testName,
//        description = testDescription,
//        uuid = testUuid,
//        priority = Some(testPriorityUuid),
//        deadline = Deadline(testDeadlineDate, Some(testInitialDate), Some(testCompletionDate)),
//        scheduleDate = testScheduleDate,
//        state = Some(testStateUuid),
//        tedDuration = testTedDuration,
//        reoccurring = true,
//        recurrenceInterval = testRecurrenceInterval,
//        realDuration = Some(testRealDuration)
//      )
//
//      // --- CORRECTED ACTION ---
//      // We directly summon the given instance and call 'read' on it.
//      val resultTask = summon[SimpleReader[Task]].read(mockResultSet)
//
//      // Assert: The created task should be equal to our expected task.
//      resultTask should be(expectedTask)
//    }
//
//    "correctly read a ResultSet where nullable fields are null" in new Fixture {
//      // Arrange
//      when(mockResultSet.getString("name")).thenReturn(testName)
//      when(mockResultSet.getString("description")).thenReturn(testDescription)
//      when(mockResultSet.getObject("id", classOf[UUID])).thenReturn(testUuid)
//      when(mockResultSet.getObject("priority", classOf[UUID])).thenReturn(null)
//      when(mockResultSet.getObject("deadline_date", classOf[DateTime])).thenReturn(testDeadlineDate)
//      when(mockResultSet.getObject("deadline_initialDate", classOf[DateTime])).thenReturn(null)
//      when(mockResultSet.getObject("deadline_completionDate", classOf[DateTime])).thenReturn(null)
//      when(mockResultSet.getString("scheduleDate")).thenReturn(testScheduleDate.toString)
//      when(mockResultSet.getObject("state", classOf[UUID])).thenReturn(null)
//      when(mockResultSet.getString("tedDuration")).thenReturn(testTedDuration.toString)
//      when(mockResultSet.getBoolean("reoccuring")).thenReturn(false)
//      when(mockResultSet.getObject("recurrenceInterval", classOf[Period])).thenReturn(testRecurrenceInterval)
//      when(mockResultSet.getObject("realDuration", classOf[Period])).thenReturn(null)
//
//      val expectedTask = Task(
//        name = testName,
//        description = testDescription,
//        uuid = testUuid,
//        priority = None,
//        deadline = Deadline(testDeadlineDate, None, None),
//        scheduleDate = testScheduleDate,
//        state = None,
//        tedDuration = testTedDuration,
//        reoccurring = false,
//        recurrenceInterval = testRecurrenceInterval,
//        realDuration = None
//      )
//
//      // --- CORRECTED ACTION ---
//      val resultTask = summon[SimpleReader[Task]].read(mockResultSet)
//
//      // Assert
//      resultTask should be(expectedTask)
//    }
//
//    "not implement readIdx" in new Fixture {
//      assertThrows[NotImplementedError] {
//        // --- CORRECTED ACTION ---
//        summon[SimpleReader[Task]].readIdx(mockResultSet, 1)
//      }
//    }
//
//    "not implement readName" in new Fixture {
//      assertThrows[NotImplementedError] {
//        // --- CORRECTED ACTION ---
//        summon[SimpleReader[Task]].readName(mockResultSet, "any-name")
//      }
//    }
//  }
//}