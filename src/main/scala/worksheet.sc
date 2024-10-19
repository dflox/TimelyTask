import com.github.nscala_time.time.Imports.*

val processStart: DateTime = DateTime.now()
val processEnd: DateTime = processStart + (1.hours + 10.minutes + 5.seconds)
val elapsed: Interval = processStart to processEnd

println(s"Process started at ${processStart.toString("HH:mm:ss")}")

//Print all the names of the days, starting with today and going forward for x days.
val today: DateTime = DateTime.now()
val numberOfDays: Int = 7
val days: List[DateTime] = (0 until numberOfDays).map(today + _.days).toList
days.foreach(day => println(day.dayOfWeek.getAsText))

// test json serialization
import util.FileLoader
import model.Config
import model.settings.FileType.JSON
import io.circe.generic.auto._

val config: Config = Config.defaultConfig
FileLoader.save[Config](JSON, "./", List(config))


