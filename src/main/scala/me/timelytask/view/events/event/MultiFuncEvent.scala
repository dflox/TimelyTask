package me.timelytask.view.events.event

import me.timelytask.model.utility.InputError
import me.timelytask.view.events.argwrapper.ArgWrapper

import scala.reflect.ClassTag

trait MultiFuncEvent[ArgsType, Args <: ArgWrapper[?, ArgsType, Args]]
(handlers: List[TypeSensitiveFunc[?, ArgsType, Args]]) {
  def call[T: ClassTag](args: Args): Option[InputError] = {
    handlers.find {handler =>
      handler.typeTag == implicitly[ClassTag[T]]
    } match
      case Some(event) => event(args)
      case None => Some(InputError("No fitting handler found!"))
  }
}