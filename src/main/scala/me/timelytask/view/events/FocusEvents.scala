package me.timelytask.view.events

// Focus Events
case class FocusPrevious() extends Event[Unit]
case object FocusPrevious extends EventCompanion[FocusPrevious, Unit]

case class FocusNext() extends Event[Unit]
case object FocusNext extends EventCompanion[FocusNext, Unit]

case class FocusUp() extends Event[Unit]
case object FocusUp extends EventCompanion[FocusUp, Unit]

case class FocusDown() extends Event[Unit]
case object FocusDown extends EventCompanion[FocusDown, Unit]

case class FocusLeft() extends Event[Unit]
case object FocusLeft extends EventCompanion[FocusLeft, Unit]

case class FocusRight() extends Event[Unit]
case object FocusRight extends EventCompanion[FocusRight, Unit]
