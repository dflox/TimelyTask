package me.timelytask.view.viewmodel

import com.github.nscala_time.time.Imports.DateTime
import me.timelytask.model.Model
import me.timelytask.util.Publisher
import me.timelytask.model.settings.ViewType
import me.timelytask.view.viewmodel.elemts.FocusElementGrid

trait ViewModel[ViewType](modelPublisher: Publisher[Model]) {
  val focusElementGrid: Option[FocusElementGrid]
  val model: () => Option[Model] = () => {modelPublisher.getValue}
  val today: DateTime = DateTime.now()
}
