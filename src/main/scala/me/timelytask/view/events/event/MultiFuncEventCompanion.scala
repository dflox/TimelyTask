package me.timelytask.view.events.event

import me.timelytask.view.events.argwrapper.ArgWrapper

trait MultiFuncEventCompanion[ArgsType, Args <: ArgWrapper[?, ArgsType, Args], EventType
<: MultiFuncEvent[ArgsType, Args]] {
  protected var handlers: List[TypeSensitiveFunc[?, ArgsType, Args]] = Nil

  def addHandler[T](newHandler: TypeSensitiveFunc[T, ArgsType, Args]): Unit = {
    handlers = handlers :+ newHandler
  }

  def createEvent: EventType = {
    if (handlers.isEmpty) throw new Exception("Handler not set for companion object")
    create
  }

  protected def create: EventType
}
