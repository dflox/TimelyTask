package view

import view._
import com.github.nscala_time.time.Imports._

case class Header(){
  val userName: String = System.getProperty("user.name")
  private val greeting = "Hello " + userName + ","
  val helper: TUIHelper = new TUIHelper();
  
  def getHeader(width: Int): String = {
    greeting + TUIHelper.createSpace(width-greeting.length) + DateTime.now().toString("dd. MMMM YYYY")
      + "\n" + "Welcome to TimelyTask! \n"
  }
}
