package me.timelytask.view.views

import me.timelytask.view.viewmodel.dialogmodel.DialogModel

trait Dialog[T, RenderType] {
  def apply(): Option[T]
  val dialogModel: Option[DialogModel[T]]
}
