package me.timelytask.util

import java.util.concurrent.{CancellationException, ExecutorService, Executors}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.util.{Failure, Success, Try}

/**
 * A wrapper around a Future that allows for cancellation and provides a way to
 * handle success and failure callbacks.
 *
 * @param future   The underlying Future
 * @param promise  The Promise associated with the Future
 * @param executor The ExecutorService used to run the task
 */
class CancelableFuture[ReturnType](
                                    private val future: Future[ReturnType],
                                    private val promise: Promise[ReturnType],
                                    private val executor: ExecutorService
                                  ) {
  /**
   * Cancels the future and shuts down the executor.
   *
   * @return true if the task that was cancelled never started, false otherwise
   */
  def cancel(): Boolean = {
    val cancelResult = executor.shutdownNow()
    promise.tryFailure(new CancellationException("Task was cancelled"))
    !cancelResult.isEmpty
  }

  /**
   * Awaits the result of the future.
   * Warning: Await blocks the calling thread's execution until they return, which will cause
   * performance degradation, and possibly, deadlock issues. Use Callbacks instead.
   *
   * @return The result of the future
   */
  def await(duration: Duration = Duration.Inf): Option[ReturnType] = {
    Try[ReturnType]{
      Await.result(future, duration)
    } match {
      case Success(value) => Some(value)
      case Failure(exception) => None
    }
  }

  /**
   * Returns the result of the future if it is completed.
   */
  def value(): Option[Try[ReturnType]] = {
    future.value
  }

  def isCancelled: Boolean = promise.future.value.exists(_.isFailure)

  def isCompleted: Boolean = future.isCompleted
}

object CancelableFuture {

  /**
   * Creates a cancelable future from a task.
   *
   * @param task             The code to execute
   * @param onSuccess        Optional callback for successful completion
   * @param onFailure        Optional callback for failure
   * @param providedExecutor The Executor (optional, will create a dedicated one if not
   *                         provided)
   * @return A CancelableFuture instance
   */
  def apply[ReturnType](
                         task: => ReturnType,
                         onSuccess: Option[ReturnType => Unit] = None,
                         onFailure: Option[Throwable => Unit] = None,
                         providedExecutor: Option[ExecutorService] = None): CancelableFuture[ReturnType] = {
    val executor = providedExecutor match
      case Some(value) => value
      case _ => Executors.newSingleThreadExecutor()

    implicit val context: ExecutionContext = ExecutionContext.fromExecutorService(executor)

    val promise = Promise[ReturnType]()

    val future = Future {
      val result = Try(task)
      result match {
        case Success(value) =>
          onSuccess.foreach(callback => callback(value))
          promise.success(value)
          value
        case Failure(exception) =>
          onFailure.foreach(callback => callback(exception))
          promise.tryFailure(exception)
          throw exception
      }
    }

    new CancelableFuture(future, promise, executor)
  }
}