package me.timelytask.view.viewmodel

import com.github.nscala_time.time.Imports.{DateTime, Interval, LocalTime}
import com.github.nscala_time.time.RichLocalTime
import me.timelytask.model.{Model, modelPublisher}
import me.timelytask.model.settings.{ViewType, activeViewPublisher}
import me.timelytask.model.utility.TimeSelection
import me.timelytask.util.Publisher

trait ViewModel {
  val model: Model
  val today: DateTime = DateTime.now()
}

def defaultViewModel(using activeViewPublisher: Publisher[ViewType], modelPublisher: Publisher[Model]): ViewModel = {
  activeViewPublisher.getValue match {
    case ViewType.CALENDAR => CalendarViewModel(modelPublisher.getValue,
      TimeSelection.defaultTimeSelection)
    case ViewType.TASK => TaskModel(modelPublisher.getValue)
  }
}

given viewModelPublisher: Publisher[ViewModel] = Publisher[ViewModel](defaultViewModel)
