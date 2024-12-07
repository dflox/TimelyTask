package me.timelytask.view.events

import me.timelytask.model.utility.InputError

// Focus Events

case class FocusPrevious(handler: Handler[Unit],
                         isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)

case object FocusPrevious extends EventCompanion[FocusPrevious, Unit] {
  override protected def create: FocusPrevious = FocusPrevious(handler.get, isPossible.get)
}

case class FocusNext(handler: Handler[Unit],
                     isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)

case object FocusNext extends EventCompanion[FocusNext, Unit] {
  override protected def create: FocusNext = FocusNext(handler.get, isPossible.get)
}

case class FocusUp(handler: Handler[Unit],
                   isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)

case object FocusUp extends EventCompanion[FocusUp, Unit] {
  override protected def create: FocusUp = FocusUp(handler.get, isPossible.get)
}

case class FocusDown(handler: Handler[Unit],
                     isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)

case object FocusDown extends EventCompanion[FocusDown, Unit] {
  override protected def create: FocusDown = FocusDown(handler.get, isPossible.get)
}

case class FocusLeft(handler: Handler[Unit],
                     isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)

case object FocusLeft extends EventCompanion[FocusLeft, Unit] {
  override protected def create: FocusLeft = FocusLeft(handler.get, isPossible.get)
}

case class FocusRight(handler: Handler[Unit],
                      isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)

case object FocusRight extends EventCompanion[FocusRight, Unit] {
  override protected def create: FocusRight = FocusRight(handler.get, isPossible.get)
}