package view

import com.github.nscala_time.time.Imports.{DateTime, Interval, LocalTime}
import com.github.nscala_time.time.RichLocalTime
import view.*
import model.Model

trait ViewModel {
  val model: Model
  val userName: String = System.getProperty("user.name")
  val today: DateTime = DateTime.now()
}

//trait ViewModelGUI() extends ViewModel {
//}