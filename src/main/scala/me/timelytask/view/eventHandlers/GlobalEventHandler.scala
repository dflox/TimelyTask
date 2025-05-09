package me.timelytask.view.eventHandlers

import me.timelytask.controller.commands.CommandHandler
import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.util.publisher.PublisherImpl
import me.timelytask.view.viewmodel.{CalendarViewModel, TaskEditViewModel}

class GlobalEventHandler(calendarViewModelPublisher: PublisherImpl[CalendarViewModel],
                         taskEditViewModelPublisher: PublisherImpl[TaskEditViewModel],
                         modelPublisher: PublisherImpl[Model],
                         undoManager: CommandHandler,
                         activeViewPublisher: PublisherImpl[ViewType]) {

}
