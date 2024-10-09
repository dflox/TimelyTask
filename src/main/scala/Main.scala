def main(args:Array[String]) = {
  val userName = System.getProperty("user.name")
  val greeting = "Hello " + userName + ","
  printLine()
  println(greeting)
  println("Welcome to TimelyTask! \n")
  printLine()
  println("Calender" + createSpace(width - 20) + date)
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
val width = 100
val date = "07-10 oct 24"
val lettersOfDayColumn = 61
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