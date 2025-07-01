package me.timelytask.view.viewmodel.dialogmodel

import com.github.nscala_time.time.Imports.Period

case class InputDialogModelPeriod(
    description: String,
    default: Option[Period] = None)
    extends InputDialogModel[Period]
