import com.github.nscala_time.time.Imports.*
import org.joda.time.format.PeriodFormat

val processStart: DateTime = DateTime.now()
val processEnd: DateTime = processStart + (1.hours + 10.minutes + 5.seconds)
val elapsed: Interval = processStart to processEnd

println(elapsed.toPeriod.toString(PeriodFormat.getDefault))

//Print all the names of the days, starting with today and going forward for x days.
val today: DateTime = DateTime.now()
val numberOfDays: Int = 7
val days: List[DateTime] = (0 until numberOfDays).map(today + _.days).toList
days.foreach(day => println(day.dayOfWeek.getAsText))
