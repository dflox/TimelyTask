package me.timelytask.view.events.event

import me.timelytask.model.utility.InputError
import me.timelytask.view.events.argwrapper.ArgWrapper

import scala.reflect.ClassTag

trait TypeSensitiveFunc[T, ArgsType, Args <: ArgWrapper[?, ArgsType, Args]] {
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
