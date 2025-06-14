import com.github.nscala_time.time.Imports.*
import me.timelytask.model.utility.TimeSelection
import me.timelytask.model.{Model, Task}
import me.timelytask.view.viewmodel.CalendarViewModel
import me.timelytask.view.views.viewImpl.tui.CalendarViewStringFactory

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
//import me.timelytask.model.Config
//import me.timelytask.model.settings.FileType.JSON
//import io.circe.generic.auto._
//////
//val config: Config = Config.default
//FileLoader.save[Config](JSON, "./", List(config))
////

trait testTrait {
  object testObject {
    def testMethod(): String = "testObject: " + x

    var x = 5
  }
}

object class1 extends testTrait

object class2 extends testTrait

class1.testObject.x = 10
class2.testObject.x = 20

class1.testObject.testMethod()
class2.testObject.testMethod()