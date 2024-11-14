package me.timelytask.controller

import me.timelytask.util.Publisher
import me.timelytask.model.{Model, modelPublisher}
import me.timelytask.model.settings.{ViewType, activeViewPublisher}
import me.timelytask.view.viewmodel.{ViewModel, viewModelPublisher}

trait CoreInitializer {
  summon[Publisher[ViewType]]
  summon[Publisher[ViewModel]]
  summon[Publisher[Model]]
  CalendarController
  TaskController
  PersistenceController
}
