import me.timelytask.controller.{CalendarController, PersistenceController}
import me.timelytask.model.Model
import me.timelytask.model.settings.{NextDay, ViewType}
import me.timelytask.model.utility.TimeSelection
import me.timelytask.util.Publisher
import me.timelytask.model.modelPublisher
import me. timelytask. model. settings. activeViewPublisher
import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel, viewModelPublisher}

summon[Publisher[ViewType]]
summon[Publisher[ViewModel]]
summon[Publisher[Model]]
CalendarController
PersistenceController

val timeSelection = TimeSelection.defaultTimeSelection
val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
viewModelPublisher.update(calendarViewModel)

NextDay.call 
viewModelPublisher.getValue.asInstanceOf[CalendarViewModel].timeSelection.day