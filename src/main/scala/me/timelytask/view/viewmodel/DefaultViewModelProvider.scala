package me.timelytask.view.viewmodel

import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.model.utility.TimeSelection
import me.timelytask.util.Publisher

object DefaultViewModelProvider {
  def defaultViewModel(using activeViewPublisher: Publisher[ViewType],
                       modelPublisher: Publisher[Model]): ViewModel = {
    activeViewPublisher.getValue match {
      case ViewType.CALENDAR => CalendarViewModel(TimeSelection.defaultTimeSelection)
    }
  }
}

//given viewModelPublisher: Publisher[ViewModel] = Publisher[ViewModel](DefaultViewModelProvider.defaultViewModel)