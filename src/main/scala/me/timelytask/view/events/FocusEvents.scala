package me.timelytask.view.events

// Focus Events

case class FocusPrevious(handler: Handler[Unit],
                         argumentProvider: ArgumentProvider[Unit],
                         isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object FocusPrevious extends EventCompanion[FocusPrevious, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): FocusPrevious = FocusPrevious(handler.get,
    argumentProvider, isPossible)
}

case class FocusNext(handler: Handler[Unit],
                     argumentProvider: ArgumentProvider[Unit],
                     isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object FocusNext extends EventCompanion[FocusNext, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): FocusNext = FocusNext(handler.get,
    argumentProvider, isPossible)
}

case class FocusUp(handler: Handler[Unit],
                   argumentProvider: ArgumentProvider[Unit],
                   isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object FocusUp extends EventCompanion[FocusUp, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): FocusUp = FocusUp(handler.get,
    argumentProvider, isPossible)
}

case class FocusDown(handler: Handler[Unit],
                     argumentProvider: ArgumentProvider[Unit],
                     isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object FocusDown extends EventCompanion[FocusDown, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): FocusDown = FocusDown(handler.get,
    argumentProvider, isPossible)
}

case class FocusLeft(handler: Handler[Unit],
                     argumentProvider: ArgumentProvider[Unit],
                     isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object FocusLeft extends EventCompanion[FocusLeft, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): FocusLeft = FocusLeft(handler.get,
    argumentProvider, isPossible)
}

case class FocusRight(handler: Handler[Unit],
                      argumentProvider: ArgumentProvider[Unit],
                      isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object FocusRight extends EventCompanion[FocusRight, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                                isPossible: () => Boolean): FocusRight = FocusRight(handler.get,
    argumentProvider, isPossible)
}