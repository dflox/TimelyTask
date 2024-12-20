package me.timelytask.view.events

import me.timelytask.model.utility.InputError
import me.timelytask.view.events.argwrapper.ArgWrapper

import scala.util.Try

trait TypeSensitiveHandler[T, ArgsType, Args <: ArgWrapper[?, ArgsType, Args]] {
  def apply(args: Args): Boolean
}

trait MultiHandlerEvent[ArgsType, Args <: ArgWrapper[?, ArgsType, Args]]
(handlers: List[TypeSensitiveHandler[?, ArgsType, Args]],
 isPossibles: List[Args => Option[InputError]]) {
  def call[T](args: Args): Boolean = {
    if isPossibles.exists {
      case isPossible: Args => isPossible(args).isEmpty
      case _ => false
    }
    then handlers.exists {
      case h: TypeSensitiveHandler[T, ArgsType, Args] => h(args)
      case _ => false
    }
    else false
  }
}

trait MultiHandlerEventCompanion[ArgsType, Args <: ArgWrapper[?, ArgsType, Args], EventType
<: MultiHandlerEvent[ArgsType, Args]] {
  protected var handlers: List[TypeSensitiveHandler[?, ArgsType, Args]] = Nil
  protected var isPossibles: List[Args => Option[InputError]] = Nil

  def addHandler(newHandler: TypeSensitiveHandler[?, ArgsType, Args], isPossible: Args => 
    Option[InputError]): Unit = {
    handlers = handlers :+ newHandler
    isPossibles = isPossibles :+ isPossible
  }

  def createEvent: EventType = {
    if (handlers.isEmpty) throw new Exception("Handler not set for companion object")
    create
  }

  protected def create: EventType
}