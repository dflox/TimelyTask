package me.timelytask.view.viewmodel

import com.github.nscala_time.time.Imports.{DateTime, Interval, LocalTime}
import com.github.nscala_time.time.RichLocalTime
import me.timelytask.model.Model

trait ViewModel {
  val model: Model
  val userName: String = System.getProperty("user.name")
  val today: DateTime = DateTime.now()
}

//trait ViewModelGUI() extends ViewModel {
//}