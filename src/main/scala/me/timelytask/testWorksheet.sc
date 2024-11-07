import com.github.nscala_time.time.Imports.*
import me.timelytask.model.Model
import me.timelytask.model.utility.TimeSelection
import me.timelytask.view.tui.CalendarTUI
import me.timelytask.view.viewmodel.{CalendarViewModel, ViewModel}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

val fixedDateTime = new DateTime(2024, 11, 6, 16, 55)
val timeSelection = TimeSelection(fixedDateTime, 7, 1.hour)
val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
val viewModel: ViewModel = calendarViewModel
val output = CalendarTUI.update(viewModel)