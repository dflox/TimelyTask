package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.CommandHandler
import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.util.Publisher
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}

// TODO: Adapt this class to the new event handling system, add GlobalEventResolver and extend 
//  the Keymap to include the global events
class GlobalEventHandler(calendarViewModelPublisher: Publisher[CalendarViewModel],
                         taskEditViewModelPublisher: Publisher[TaskEditViewModel],
                         modelPublisher: Publisher[Model],
                         undoManager: CommandHandler,
                         activeViewPublisher: Publisher[ViewType]) {
}
