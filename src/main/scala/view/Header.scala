package view

import util.tuiHelper._
import com.github.nscala_time.time.Imports._

case class Header(){
  val userName = System.getProperty("user.name")
  private val greeting = "Hello " + userName + ","
  
  def getHeader(width: Int): String = {
    greeting + createSpace(width-greeting.length) + DateTime.now().toString("dd. MMMM YYYY")
      + "\n" + "Welcome to TimelyTask! \n"
  }
}
