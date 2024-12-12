package me.timelytask.view.viewmodel.elemts

import me.timelytask.view.viewmodel.dialogmodel.{DialogModel, InputDialogModel}

class InputField[T](val description: String, val defaultInput: Option[T]) 
  extends Focusable[T]{

  override val dialogModel: InputDialogModel[T] = InputDialogModel[T](description, defaultInput)
}
