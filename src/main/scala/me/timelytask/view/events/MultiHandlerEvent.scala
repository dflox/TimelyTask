package me.timelytask.view.events

import me.timelytask.model.utility.InputError
import me.timelytask.view.events.argwrapper.ArgWrapper

import scala.reflect.ClassTag
import scala.util.Try

trait TypeSensitiveHandler[T, ArgsType, Args <: ArgWrapper[?, ArgsType, Args]] {
  def typeTag: ClassTag[T]
  
  protected def isPossible(args: Args): Option[InputError]
  protected def call(args: Args): Boolean
  def apply(args: Args): Option[InputError] = {
    isPossible(args) match
      case None => if call(args) then None
                   else Some(InputError("Event failed unexpectedly!"))
      case Some(value) => Some(value)
  }
}

trait MultiHandlerEvent[ArgsType, Args <: ArgWrapper[?, ArgsType, Args]]
(handlers: List[TypeSensitiveHandler[?, ArgsType, Args]]) {
  def call[T: ClassTag](args: Args): Option[InputError] = {
    handlers.find {handler =>
      handler.typeTag == implicitly[ClassTag[T]]
    } match
      case Some(event) => event(args)
      case None => Some(InputError("No fitting handler found!"))
  }
}

trait MultiHandlerEventCompanion[ArgsType, Args <: ArgWrapper[?, ArgsType, Args], EventType
<: MultiHandlerEvent[ArgsType, Args]] {
  protected var handlers: List[TypeSensitiveHandler[?, ArgsType, Args]] = Nil

  def addHandler[T](newHandler: TypeSensitiveHandler[T, ArgsType, Args]): Unit = {
    handlers = handlers :+ newHandler
  }

  def createEvent: EventType = {
    if (handlers.isEmpty) throw new Exception("Handler not set for companion object")
    create
  }

  protected def create: EventType
}