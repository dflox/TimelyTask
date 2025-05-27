package me.timelytask.view.viewmodel.dialogmodel

class ConfirmDialogModel(override val description: String,
                         val default: Option[Boolean] = None,
                         val question: String) extends DialogModel[Boolean]
