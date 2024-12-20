package me.timelytask.view.events

import me.timelytask.model.utility.InputError
import me.timelytask.view.events.argwrapper.ArgWrapper

import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

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
  protected var isPossible: Option[Args => Option[InputError]] = None

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

trait TypeSensitiveEventCompanion[EventType <: Event[Args], Args] {
  protected var wrappers: List[TypeSensitiveWrapper[?, Args]] = Nil

  def addHandler[T: ClassTag](newHandler: Handler[Args], isPossible: Args => Option[InputError])
  : Unit = {
    wrappers = TypeSensitiveWrapper[T, Args](newHandler, isPossible) :: wrappers
  }

  protected class TypeSensitiveWrapper[T: ClassTag, Args](val handler: Handler[Args],
                                                          val isPossible: Args => Option[InputError]
                                                         ) {
    def matchesType[A: ClassTag]: Boolean =
      implicitly[ClassTag[T]].runtimeClass == implicitly[ClassTag[A]].runtimeClass
  }

  def createEvent[T: ClassTag]: EventType = {
    Try[EventType] {
      wrappers.find(_.matchesType[T]) match {
        case Some(wrapper) => create[T](wrapper.handler, wrapper.isPossible)
        case None => throw new Exception(
          s"Handler of requested type is not set for companion object")
      }
    } match {
      case Success(event) => event
      case Failure(ex) => throw ex
    }
  }

  protected def create[T: ClassTag](handler: Handler[Args], isPossible: Args => Option[InputError])
  : EventType
}
