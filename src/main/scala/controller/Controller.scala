package controller

import view.*
import model.*
import com.github.nscala_time.time.Imports.*

import java.awt.Color
import scala.collection.immutable.HashSet

case class Controller(view: View[List[Task]]){


  def run(): Unit = {
    print(view.render(List(Task("Task1", "Description1", Priority(id = 2, rank = 1, name = "High", color = Color.BLUE,
      description = "Hiqh Priority Tasks", daysPreDeadline = 5, postponable = false),
      Deadline(DateTime.now(), None, None), Period.days(1), HashSet[Task](), false, Period.days(1),
      Status(id = 1, name = "todo", description = "noch zu tun", color = Color.RED)))))
  }
}
