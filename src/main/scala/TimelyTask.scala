import controller._
import view._
import model._

object TimelyTask {
  val view: TUI = new TUI()
  val controller: Controller = new Controller(view)
  //val model: Model = new Model(controller)
  def main(args: Array[String]): Unit = {
    controller.run()
  }
}
