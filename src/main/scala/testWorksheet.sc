import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import com.github.nscala_time.time.Imports._
import model.utility.*
import view.tui.CalendarTUI
import view.viewmodel.*
import model.*
import model.Model

val fixedDateTime = new DateTime(2024, 11, 6, 16, 55)
val timeSelection = TimeSelection(fixedDateTime, 7, 1.hour)
val calendarViewModel = CalendarViewModel(Model.default, timeSelection)
val viewModel: ViewModel = calendarViewModel
val output = CalendarTUI.update(viewModel)