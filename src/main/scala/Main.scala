import com.github.nscala_time.time.Imports.*

def main(args:Array[String]) = {
  val userName = System.getProperty("user.name")
  val greeting = "Hello " + userName + ","
  printLine()
  println(greeting)
  println("Welcome to TimelyTask! \n")
  printLine()
  println("Calender" + createSpace(width - 20) + getDatePeriod(getFirstDayOfWeek(dateToday), 7, "dd.", "dd. MMM yy", " - "))
  printLine()
  println("Time |" +
          "Monday" + createSpace(spaceBetweenDays()) + "|" +
          "Thusday" + createSpace(spaceBetweenDays()) + "|" +
          "Wednesday" +  createSpace(spaceBetweenDays()) +"|" +
          "Thursday" + createSpace(spaceBetweenDays()) + "|" +
          "Friday" + createSpace(spaceBetweenDays()) + "|" +
          "Saturday" + createSpace(spaceBetweenDays()) + "|" + "Sunday")
  printLine()
  println("Type:(not implemented yet) to add a task")

}

// Variables
val width = 100
val dateToday: DateTime = DateTime.now()
val lettersOfDayColumn = 61

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

def spaceBetweenDays(): Int = {
  (width - lettersOfDayColumn) / 7
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

def printTable(width: Int, startDay: DateTime, period: Int, tasks: List[Task]): Unit = {
  val days: List[DateTime] = (0 until period).map(startDay + _.days).toList
  days.foreach(day => {
    print(day.toString("dd. MMM yy") + " |")
    tasks.foreach(task => {
      print(task.name + createSpace(spaceBetweenDays() - task.name.length) + "|")
    })
    println()
  })
}