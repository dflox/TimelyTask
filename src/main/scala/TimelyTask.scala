import controller._
import view._
import model._

object TimelyTask{
  val window: Window = new Window()//val model: Model = new Model(controller)
  def main(args: Array[String]): Unit = {
    window.run()
  }
}
