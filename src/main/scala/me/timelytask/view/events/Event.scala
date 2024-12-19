package me.timelytask.view.events

import me.timelytask.model.utility.InputError
import me.timelytask.view.events.argwrapper.ArgWrapper

import scala.util.{Try, Success, Failure}

trait Handler[Args] {
  def apply(args: Args): Boolean
}

trait Event[Args](handler: Handler[Args],
                  isPossible: Args => Option[InputError]) {
  def call(args: Args): Boolean = {
    if (isPossible(args).nonEmpty) handler(args)
    else false
  }
}

trait EventCompanion[EventType <: Event[Args], Args] {
  protected var handler: Option[Handler[Args]] = None
  protected var isPossible: Option[ Args => Option[InputError]] = None
  
  def setHandler(newHandler: Handler[Args], newIsPossible: Args => Option[InputError]): Unit = {
    handler = Some(newHandler)
    isPossible = Some(newIsPossible)
  }

  def createEvent: EventType = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    create
  }

  protected def create: EventType
}

trait TypeSensitiveEventCompanion[EventType <: Event[Args], Args]  {
  protected var wrappers: List[TypeSensitiveWrapper[?, Args]] = Nil

  def addHandler[T](newHandler: Handler[Args], isPossible: Args =>
    Option[InputError]): Unit = {
    wrappers = TypeSensitiveWrapper[T, Args](newHandler, isPossible) :: wrappers
  }

  protected class TypeSensitiveWrapper[T, Args](val handler: Handler[Args],
                                              val isPossible: Args => Option[InputError])

  def createEvent[T]: EventType = {
    Try[EventType] {
      create[T](wrappers.find(_.isInstanceOf[TypeSensitiveWrapper[T, Args]]).get.handler,
        wrappers.find(_.isInstanceOf[TypeSensitiveWrapper[T, Args]]).get.isPossible)
    } match
      case Success(event) => event
      case Failure(_) => throw new Exception("Handler of requested type is not set for companion " +
        "object")
  }

  protected def create[T](handler: Handler[Args], isPossible: Args => Option[InputError]): EventType
}
