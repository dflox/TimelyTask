package me.timelytask.view.viewmodel

import com.github.nscala_time.time.Imports.DateTime
import me.timelytask.model.Model
import me.timelytask.util.Publisher

trait ViewModel {
  val modelPublisher: Publisher[Model]
  val model: ()=>Model = () => {modelPublisher.getValue}
  val today: DateTime = DateTime.now()
}
