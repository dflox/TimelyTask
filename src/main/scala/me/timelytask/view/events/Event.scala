package me.timelytask.view.events

trait Handler[Args] {
  def apply(args: Args): Boolean
}

trait Event[Args] {
  private var handler: Option[Handler[Args]] = None

  def setHandler(newHandler: Handler[Args]): Unit = {
    if (handler.isDefined) throw new Exception("Handler already set")
    handler = Some(newHandler)
  }

  def call(args: Args): Boolean = {
    handler match {
      case Some(h) => h(args)
      case None => false
    }
  }
}

trait EventCompanion[T <: Event[Args], Args] {
  private var handler: Option[Handler[Args]] = None

  def setHandler(newHandler: Handler[Args]): Unit = {
    handler = Some(newHandler)
  }

  def createEvent(): T = {
    if (handler.isEmpty) throw new Exception("Handler not set for companion object")
    val cmd = create()
    cmd.setHandler(handler.get)
    cmd
  }

  protected def create(): T = {
    val constructor = getClass.getDeclaringClass.getDeclaredConstructor()
    constructor.newInstance().asInstanceOf[T]
  }
}
