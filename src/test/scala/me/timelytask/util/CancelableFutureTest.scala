package me.timelytask.util

import org.scalatest.concurrent.Eventually // Import the key tool for this test
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Millis, Span}
import java.util.concurrent.{CancellationException, CountDownLatch, TimeUnit}
import scala.util.{Failure, Success}

class CancelableFutureTest extends AnyFunSuite with Matchers with Eventually {

  implicit override val patienceConfig: PatienceConfig =
    PatienceConfig(timeout = Span(2000, Millis), interval = Span(50, Millis))

  test("should execute task and call onSuccess on completion") {
    // Arrange
    val successLatch = new CountDownLatch(1)
    var capturedResult: Option[Int] = None

    val successCallback = (result: Int) => {
      capturedResult = Some(result)
      successLatch.countDown()
    }

    // Act
    val cf = CancelableFuture({
      Thread.sleep(50)
      1 + 1
    }, onSuccess = Some(successCallback))

    // Assert
    val callbackFinished = successLatch.await(2, TimeUnit.SECONDS)
    callbackFinished should be (true)
    capturedResult should be (Some(2))
  }

  test("should handle exception and call onFailure") {
    // Arrange
    val failureLatch = new CountDownLatch(1)
    var capturedException: Option[Throwable] = None
    val expectedException = new RuntimeException("Task failed!")

    val failureCallback = (err: Throwable) => {
      capturedException = Some(err)
      failureLatch.countDown()
    }

    // Act
    val cf = CancelableFuture(throw expectedException, onFailure = Some(failureCallback))

    // Assert
    val callbackFinished = failureLatch.await(2, TimeUnit.SECONDS)
    callbackFinished should be (true)
    capturedException should be (Some(expectedException))
  }

  test("cancel() should interrupt a running task and set state to cancelled") {
    // Arrange
    val taskStartedLatch = new CountDownLatch(1)

    // Act
    val cf = CancelableFuture({
      taskStartedLatch.countDown()
      Thread.sleep(5000) // Let this thread be interrupted
      "Should never return this"
    })

    taskStartedLatch.await(1, TimeUnit.SECONDS)
    cf.cancel()
    eventually {
      cf.isCompleted should be (true)
    }

    // Assert
    cf.isCancelled should be (true)
    cf.value() match {
      case Some(Failure(ex)) => ex shouldBe a [CancellationException]
      case other => fail(s"Future should have failed with CancellationException, but was $other")
    }
  }
}