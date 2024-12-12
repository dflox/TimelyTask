package me.timelytask.util

import java.util.concurrent.{CancellationException, Executors}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future, Promise}

class ApplicationThread[ReturnType] {
  private val executor = Executors.newSingleThreadExecutor()
  protected implicit val context: ExecutionContext = ExecutionContext.fromExecutorService(executor)

  def run(code: => ReturnType): CancelableTask[ReturnType] = {
    val promise = Promise[ReturnType]()

    val future = Future {
      try {
        val result = code
        promise.success(result)
        result
      } catch {
        case e: CancellationException =>
          promise.tryFailure(e)
          throw e
        case t: Throwable =>
          promise.tryFailure(t)
          throw t
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