package me.timelytask.view.viewmodel.elemts

import com.github.nscala_time.time.Imports.{DateTime, Period}
import me.timelytask.view.viewmodel.dialogmodel.{DialogModel, InputDialogModel, InputDialogModelDateTime, InputDialogModelPeriod, InputDialogModelString}

trait InputField[T](override val description: String, val defaultInput: Option[T], val
displayFunc: T => String)
  extends Focusable[T] {

  override val dialogModel: InputDialogModel[T]
}

class TextInputField(description: String, defaultInput: Option[String] = None)
  extends InputField[String](description, defaultInput, identity) {
    override val dialogModel: InputDialogModel[String] =
        InputDialogModelString(description, defaultInput)
}

class DateInputField(description: String, defaultInput: Option[DateTime] = None)
  extends InputField[DateTime](description, defaultInput, dateTime => dateTime.toString("yyyy-MM-dd HH:mm:ss")) {

    override val dialogModel: InputDialogModel[org.joda.time.DateTime] =
        InputDialogModelDateTime(description, defaultInput)
}

class PeriodInputField(description: String, defaultInput: Option[Period] = None)
  extends InputField[org.joda.time.Period](description, defaultInput, period => {
    val days = period.getDays
    val hours = period.getHours
    val minutes = period.getMinutes
    s"$days days, $hours hours, $minutes minutes"
  }) {
    override val dialogModel: InputDialogModel[org.joda.time.Period] =
        InputDialogModelPeriod(description, defaultInput)
}

