package me.timelytask.view.viewmodel.dialogmodel

case class InputDialogModel[T](description: String,
                               default: Option[T] = None) extends DialogModel[T]