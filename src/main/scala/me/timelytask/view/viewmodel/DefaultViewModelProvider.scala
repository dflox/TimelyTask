package me.timelytask.view.viewmodel

import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.model.utility.TimeSelection
import me.timelytask.util.Publisher
import me.timelytask.model.settings.activeViewPublisher

object DefaultViewModelProvider {
  def defaultViewModel(using activeViewPublisher: Publisher[ViewType],
                       modelPublisher: Publisher[Model]): ViewModel = {
    activeViewPublisher.getValue match {
      case ViewType.CALENDAR => CalendarViewModel(modelPublisher.getValue,
        TimeSelection.defaultTimeSelection)
      case ViewType.TASK => TaskEditViewModel(modelPublisher.getValue)
    }
  }
}

//given viewModelPublisher: Publisher[ViewModel] = Publisher[ViewModel](DefaultViewModelProvider.defaultViewModel)