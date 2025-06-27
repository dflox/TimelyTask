package me.timelytask.view.viewmodel.dialogmodel

trait InputDialogModel[T] extends DialogModel[T] {
  def default: Option[T]
}
