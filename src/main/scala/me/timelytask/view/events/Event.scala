package me.timelytask.view.events

trait Handler[Args] {
  def apply(args: Args): Boolean
}

trait ArgumentProvider[Args] {
  def getArgs: Args
}

trait Event[Args](handler: Handler[Args],
                  argumentProvider: ArgumentProvider[Args],
                  isPossible: () => Boolean) {
  def call(args: Args): Boolean = {
    if (isPossible()) handler.apply(argumentProvider.getArgs)
    else false
  }
}

trait EventCompanion[T <: Event[Args], Args] {
  protected var handler: Option[Handler[Args]] = None
  
  def setHandler(newHandler: Handler[Args]): Unit = {
    handler = Some(newHandler)
  }

  def createEvent(argumentProvider: ArgumentProvider[Args],isPossible: () => Boolean) : T = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    create(argumentProvider, isPossible)
  }

  protected def create(argumentProvider: ArgumentProvider[Args], isPossible: () => Boolean): T
}
