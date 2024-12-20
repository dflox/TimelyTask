package me.timelytask.view.viewmodel

import com.github.nscala_time.time.Imports.DateTime
import me.timelytask.model.Model
import me.timelytask.model.settings.ViewType
import me.timelytask.util.Publisher
import me.timelytask.view.viewmodel.dialogmodel.DialogModel
import me.timelytask.view.viewmodel.elemts.FocusElementGrid

trait ViewModel[+VT <: ViewType, +Self <: ViewModel[VT, Self]](modelPublisher: Publisher[Model]) {
  //TODO: add dialog for questions form the event handler
  protected var focusElementGrid: Option[FocusElementGrid]
  val model: () => Option[Model] = () => {
    modelPublisher.getValue
  }
  val today: DateTime = DateTime.now()

  def getFocusElementGrid: Option[FocusElementGrid] = focusElementGrid

  def interact(inputGetter: Option[DialogModel[?]] => Option[?]): Option[Self]
}
