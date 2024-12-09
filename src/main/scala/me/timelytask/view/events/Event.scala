package me.timelytask.view.events

import me.timelytask.model.utility.InputError

trait Handler[Args] {
  def apply(args: Args): Boolean
}

trait Event[Args](protected val handler: Handler[Args],
                  isPossible: Args => Option[InputError]) {
  def call(args: Args): Boolean = {
    if (isPossible(args).nonEmpty) handler.apply(args)
    else false
  }
}

trait EventCompanion[T <: Event[Args], Args] {
  protected var handler: Option[Handler[Args]] = None
  protected var isPossible: Option[ Args => Option[InputError]] = None
  
  def setHandler(newHandler: Handler[Args], isPossible: Args => Option[InputError]): Unit = {
    handler = Some(newHandler)
  }

  def createEvent: T = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    create
  }

  protected def create: T
}
