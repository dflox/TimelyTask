package me.timelytask.view.viewmodel.dialogmodel

case class InputDialogModelString(
    description: String,
    default: Option[String] = None)
    extends InputDialogModel[String]
