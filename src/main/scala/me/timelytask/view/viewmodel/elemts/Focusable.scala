package me.timelytask.view.viewmodel.elemts

import me.timelytask.view.viewmodel.dialogmodel.DialogModel

trait Focusable[T] {
  val dialogModel: DialogModel[T]
  def interact(getInput: DialogModel[T] => Option[T]): Option[T] = {
    getInput(dialogModel)
  }
}
