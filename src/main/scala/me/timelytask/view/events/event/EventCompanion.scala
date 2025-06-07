package me.timelytask.view.events.event

import me.timelytask.model.utility.InputError

trait EventCompanion[EventType <: Event[Args], Args] {
  protected var handler: Option[Func[Args]] = None
  protected var isPossible: Option[Args => Option[InputError]] = None

  def setHandler(newFunc: Func[Args], newIsPossible: Args => Option[InputError]): Unit = {
    handler = Some(newFunc)
    isPossible = Some(newIsPossible)
  }

  def createEvent: EventType = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    create
  }

  protected def create: EventType
}
