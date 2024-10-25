package view.model

import com.github.nscala_time.time.Imports.*

trait ViewModel {
  val userName: String = System.getProperty("user.name")
  val today: DateTime = DateTime.now()
}
