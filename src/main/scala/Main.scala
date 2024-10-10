import com.github.nscala_time.time.Imports.*

def main(args:Array[String]) = {
  val userName = System.getProperty("user.name")
  val greeting = "Hello " + userName + ","
  printLine()
  println(greeting)
  println("Welcome to TimelyTask! \n")
  printLine()
  println("Calender" + createSpace(width - headerLetters) + getDatePeriod(getFirstDayOfWeek(dateToday), 7, "dd.", "dd. MMM yy", " - "))
  printTable(width, getFirstDayOfWeek(dateToday), 7)
  /* println("Time |" +
          "Monday" + createSpace(spaceBetweenDays()) + "|" +
          "Thusday" + createSpace(spaceBetweenDays()) + "|" +
          "Wednesday" +  createSpace(spaceBetweenDays()) +"|" +
          "Thursday" + createSpace(spaceBetweenDays()) + "|" +
          "Friday" + createSpace(spaceBetweenDays()) + "|" +
          "Saturday" + createSpace(spaceBetweenDays()) + "|" + "Sunday")
  */
  println("Type:(not implemented yet) to add a task")

}

// Variables
val width = 150
val format = "r"
val dateToday: DateTime = DateTime.now()
val headerLetters = 25 // The amount of letters in "Calender" + the date period

// Functions

// Get the first Day of the week
def getFirstDayOfWeek(day: DateTime): DateTime = {
  day - (day.getDayOfWeek - 1).days
}

/*
  Get a time period in a given Format as String starting with a given day and going forward for a given TimePeriod
*/
def getDatePeriod(day: DateTime, timePeriod: Int, formatStart: String, formatEnd: String, separator: String): String = {
  val lastDay: DateTime = day + (timePeriod - 1).days
  day.toString(formatStart) + separator + lastDay.toString(formatEnd)
}

def createSpace(length: Int): String = {
  " " * length
}
def createLine(length: Int): String = {
  "-" * length
}
def printLine(): Unit = {
  println(createLine(width))
}

def getDaySpan(startDay: DateTime, period: Int): List[DateTime] = {
  (0 until period).map(startDay + _.days).toList
}

// Print a line, then (time, Monday, Tuesday,(starting with the start day)...) and then a line, creating a list of all Strings(time + days + |) then calculating the amount of letters to ensure equal spaces between the columnns
def printTable(width: Int, startDay: DateTime, period: Int): Unit = {
  printLine()
  val daysList = getDaySpan(startDay, period)
  var table = List[String]()
  table = table :+ "|Time  |"
  daysList.foreach(day => table = table :+ day.dayOfWeek().getAsText + "|")
  val letterAmount = table.map(_.length).sum
  val spaceBetween = ((width - table.head.length - period) / period)

  print("|time  |")
  daysList.foreach(day => print(columnSpacer(day.dayOfWeek().getAsText, spaceBetween, format) + "|")) // print the days
  println()
  printLine()

  //temp. testing
  println("spaceBetween: " + spaceBetween)
}


def columnSpacer(text: String, totalSpace: Int, format: String): String = {
  // check if the text is longer than the total space, if so cut it
  if (text.length > totalSpace) {
    return text.substring(0, totalSpace)
  }
  val space = totalSpace - text.length
  format match {
    case "l" => text + createSpace(space) // left
    case "m" => createSpace(space/2) + text + createSpace(space/2) // middle
    case "r" => createSpace(space) + text // right
  }
}