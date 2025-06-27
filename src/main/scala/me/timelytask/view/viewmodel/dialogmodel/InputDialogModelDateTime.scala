package me.timelytask.view.viewmodel.dialogmodel

import com.github.nscala_time.time.Imports.DateTime

case class InputDialogModelDateTime(
    description: String,
    default: Option[DateTime] = None)
    extends InputDialogModel[DateTime]