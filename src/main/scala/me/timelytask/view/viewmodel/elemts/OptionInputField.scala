package me.timelytask.view.viewmodel.elemts

import me.timelytask.view.viewmodel.dialogmodel.OptionDialogModel

class OptionInputField[T](val description: String = "", val options: List[T],
                          val displayFunc: T => String, val default: List[T] = Nil,
                          val minSelection: Option[Int] = Some(1), val maxSelection: Option[Int] = 
                          Some(1)) extends Focusable[T] {
  override val dialogModel: OptionDialogModel[T] = OptionDialogModel[T](description, options, 
    displayFunc, default, minSelection, maxSelection)
}
