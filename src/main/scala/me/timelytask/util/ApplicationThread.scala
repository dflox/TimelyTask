package me.timelytask.util

import java.util.concurrent.{CancellationException, Executors}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.util.{Failure, Success, Try}

class ApplicationThread[ReturnType] {
  private val executor = Executors.newSingleThreadExecutor()
  protected implicit val context: ExecutionContext = ExecutionContext.fromExecutorService(executor)

  def run(code: => ReturnType): CancelableTask[ReturnType] = {
    val promise = Promise[ReturnType]()

    val future = Future {
      val result = Try(code)
      result match {
        case Success(value) =>
          promise.success(value)
          value
        case Failure(exception) =>
          promise.tryFailure(exception)
          throw exception
      }
    }

    new CancelableTask(future, promise, executor)
  }
}

class CancelableTask[ReturnType](
                                  val future: Future[ReturnType],
                                  private val promise: Promise[ReturnType],
                                  private val executor: java.util.concurrent.ExecutorService
                                ) {
  def cancel(): Boolean = {
    val cancelResult = executor.shutdownNow()
    promise.tryFailure(new CancellationException("Task was cancelled"))

    !cancelResult.isEmpty
  }

  def await(): ReturnType = {
    Await.result(future, Duration.Inf)
  }

  def isCancelled: Boolean = promise.future.value.exists(_.isFailure)

  def isCompleted: Boolean = future.isCompleted
}