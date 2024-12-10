package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.UndoManager
import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.util.Publisher
import me.timelytask.view.events.FocusPrevious
import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}

class GlobalEventHandler(using
                         calendarViewModelPublisher: Publisher[CalendarViewModel],
                         taskEditViewModelPublisher: Publisher[TaskEditViewModel],
                         modelPublisher: Publisher[Model],
                         undoManager: UndoManager,
                         activeViewPublisher: Publisher[ViewType]) {

  FocusPrevious.setHandler((args: Unit) => {
    activeViewPublisher.update(ViewType.CALENDAR)
    None
  }, (args: Unit) => None) 
  
}
