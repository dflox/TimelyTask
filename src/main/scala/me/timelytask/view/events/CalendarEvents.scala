package me.timelytask.view.events

case class NextDay(handler: Handler[Unit], 
                   argumentProvider: ArgumentProvider[Unit],
                   isPossible: () => Boolean) 
  extends Event[Unit](handler, argumentProvider, isPossible)
case object NextDay extends EventCompanion[NextDay, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit], 
                      isPossible: () => Boolean): NextDay = NextDay(handler.get, 
    argumentProvider, isPossible)
}

case class PreviousDay(handler: Handler[Unit],
                       argumentProvider: ArgumentProvider[Unit],
                       isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object PreviousDay extends EventCompanion[PreviousDay, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                      isPossible: () => Boolean): PreviousDay = PreviousDay(handler.get,
    argumentProvider, isPossible)
}

case class NextWeek(handler: Handler[Unit],
                    argumentProvider: ArgumentProvider[Unit],
                    isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object NextWeek extends EventCompanion[NextWeek, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                      isPossible: () => Boolean): NextWeek = NextWeek(handler.get,
    argumentProvider, isPossible)
}

case class PreviousWeek(handler: Handler[Unit],
                        argumentProvider: ArgumentProvider[Unit],
                        isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object PreviousWeek extends EventCompanion[PreviousWeek, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                      isPossible: () => Boolean): PreviousWeek = PreviousWeek(handler.get,
    argumentProvider, isPossible)
}

case class GoToToday(handler: Handler[Unit],
                     argumentProvider: ArgumentProvider[Unit],
                     isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object GoToToday extends EventCompanion[GoToToday, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                      isPossible: () => Boolean): GoToToday = GoToToday(handler.get,
    argumentProvider, isPossible)
}

case class GoToDate(handler: Handler[Unit],
                    argumentProvider: ArgumentProvider[Unit],
                    isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object GoToDate extends EventCompanion[GoToDate, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                      isPossible: () => Boolean): GoToDate = GoToDate(handler.get,
    argumentProvider, isPossible)
}

case class ShowWholeWeek(handler: Handler[Unit],
                         argumentProvider: ArgumentProvider[Unit],
                         isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object ShowWholeWeek extends EventCompanion[ShowWholeWeek, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                      isPossible: () => Boolean): ShowWholeWeek = ShowWholeWeek(handler.get,
    argumentProvider, isPossible)
}

case class ShowMoreDays(handler: Handler[Unit],
                        argumentProvider: ArgumentProvider[Unit],
                        isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object ShowMoreDays extends EventCompanion[ShowMoreDays, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                      isPossible: () => Boolean): ShowMoreDays = ShowMoreDays(handler.get,
    argumentProvider, isPossible)
}

case class ShowLessDays(handler: Handler[Unit],
                        argumentProvider: ArgumentProvider[Unit],
                        isPossible: () => Boolean)
  extends Event[Unit](handler, argumentProvider, isPossible)
case object ShowLessDays extends EventCompanion[ShowLessDays, Unit]{
  override protected def create(argumentProvider: ArgumentProvider[Unit],
                      isPossible: () => Boolean): ShowLessDays = ShowLessDays(handler.get,
    argumentProvider, isPossible)
}