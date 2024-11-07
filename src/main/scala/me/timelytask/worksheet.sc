import com.github.nscala_time.time.Imports.*
import me.timelytask.model.{Model, Task}
import me.timelytask.view.tui.CalendarTUI
import model.{TimeSelection}
import view.CalendarViewModel

val processStart: DateTime = DateTime.now()
val processEnd: DateTime = processStart + (1.hours + 10.minutes + 5.seconds)
val elapsed: Interval = processStart to processEnd

println(s"Process started at ${processStart.toString("HH:mm:ss")}")

//Print all the names of the days, starting with today and going forward for x days.
val today: DateTime = DateTime.now()
val numberOfDays: Int = 7
val days: List[DateTime] = (0 until numberOfDays).map(today + _.days).toList
days.foreach(day => println(day.dayOfWeek.getAsText))

//// test json serialization
//import model.Config
//import model.settings.FileType.JSON
//import io.circe.generic.auto._
////
//val config: Config = Config.defaultConfig
//FileLoader.save[Config](JSON, "./", List(config))
//

new Period(0,0,0,0,0,121,0,0).normalizedStandard()


val timeSelection = TimeSelection(new DateTime(2024, 10, 14, 22, 0), 2, 5.hour)
val tasks = List(Task.exampleTask)
val spacePerColumn = 10
CalendarTUI.createRows(1.hour, 5, timeSelection, tasks, spacePerColumn)

val timeSelection2 = TimeSelection(DateTime.now().withPeriodAdded(2.hour, -1), 2, 5.hour)
val rows2 = CalendarTUI.createRows(1.hour, 5, timeSelection2, tasks, spacePerColumn)
rows2

DateTime.now().toString("EEEE dd")

val viewModel: CalendarViewModel = new CalendarViewModel(Model.default, TimeSelection.defaultTimeSelection)

viewModel.copy(timeSelection = viewModel.timeSelection.copy(day = viewModel.timeSelection.day + 1.days))

