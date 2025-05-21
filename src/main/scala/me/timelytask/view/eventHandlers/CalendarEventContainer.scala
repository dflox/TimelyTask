package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.Command
import me.timelytask.model.Model
import me.timelytask.model.settings.{CALENDAR, ViewType}
import me.timelytask.util.Publisher
import me.timelytask.view.events.Event
import me.timelytask.view.viewmodel.CalendarViewModel

import java.util.concurrent.LinkedBlockingQueue

trait CalendarEventContainer extends EventContainer[CALENDAR, CalendarViewModel] {
  def NextDay: Event[Unit]

  def PreviousDay: Event[Unit]

  def NextWeek: Event[Unit]

  def PreviousWeek: Event[Unit]
}
