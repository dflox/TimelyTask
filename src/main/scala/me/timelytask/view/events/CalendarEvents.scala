package me.timelytask.view.events

case class NextDay() extends Event[Unit]
case object NextDay extends EventCompanion[NextDay, Unit]

case class PreviousDay() extends Event[Unit]
case object PreviousDay extends EventCompanion[PreviousDay, Unit]

case class NextWeek() extends Event[Unit]
case object NextWeek extends EventCompanion[NextWeek, Unit]

case class PreviousWeek() extends Event[Unit]
case object PreviousWeek extends EventCompanion[PreviousWeek, Unit]

case class GoToToday() extends Event[Unit]
case object GoToToday extends EventCompanion[GoToToday, Unit]

case class GoToDate() extends Event[Unit]
case object GoToDate extends EventCompanion[GoToDate, Unit]

case class ShowWholeWeek() extends Event[Unit]
case object ShowWholeWeek extends EventCompanion[ShowWholeWeek, Unit]

case class ShowMoreDays() extends Event[Unit]
case object ShowMoreDays extends EventCompanion[ShowMoreDays, Unit]

case class ShowLessDays() extends Event[Unit]
case object ShowLessDays extends EventCompanion[ShowLessDays, Unit]
