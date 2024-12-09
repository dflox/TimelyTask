package me.timelytask.view.events

import me.timelytask.model.utility.InputError

case class NextDay(handler: Handler[Unit],
                   isPossible: Unit => Option[InputError]) 
  extends Event[Unit](handler, isPossible)
case object NextDay extends EventCompanion[NextDay, Unit]{
  override protected def create: NextDay = NextDay(handler.get, isPossible.get)
}

case class PreviousDay(handler: Handler[Unit],
                       isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)
case object PreviousDay extends EventCompanion[PreviousDay, Unit]{
  override protected def create: PreviousDay = PreviousDay(handler.get, isPossible.get)
}

case class NextWeek(handler: Handler[Unit],
                    isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)
case object NextWeek extends EventCompanion[NextWeek, Unit]{
  override protected def create: NextWeek = NextWeek(handler.get, isPossible.get)
}

case class PreviousWeek(handler: Handler[Unit],
                        isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)
case object PreviousWeek extends EventCompanion[PreviousWeek, Unit]{
  override protected def create: PreviousWeek = PreviousWeek(handler.get, isPossible.get)
}

case class GoToToday(handler: Handler[Unit],
                     isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)
case object GoToToday extends EventCompanion[GoToToday, Unit]{
  override protected def create: GoToToday = GoToToday(handler.get, isPossible.get)
}

case class GoToDate(handler: Handler[Unit],
                    isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)
case object GoToDate extends EventCompanion[GoToDate, Unit]{
  override protected def create: GoToDate = GoToDate(handler.get, isPossible.get)
}

case class ShowWholeWeek(handler: Handler[Unit],
                         isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)
case object ShowWholeWeek extends EventCompanion[ShowWholeWeek, Unit]{
  override protected def create: ShowWholeWeek = ShowWholeWeek(handler.get, isPossible.get)
}

case class ShowMoreDays(handler: Handler[Unit],
                        isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)
case object ShowMoreDays extends EventCompanion[ShowMoreDays, Unit]{
  override protected def create: ShowMoreDays = ShowMoreDays(handler.get, isPossible.get)
}

case class ShowLessDays(handler: Handler[Unit],
                        isPossible: Unit => Option[InputError])
  extends Event[Unit](handler, isPossible)
case object ShowLessDays extends EventCompanion[ShowLessDays, Unit]{
  override protected def create: ShowLessDays = ShowLessDays(handler.get, isPossible.get)
}