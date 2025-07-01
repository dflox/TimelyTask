package me.timelytask.view.viewmodel.dialogmodel

case class OptionDialogModel[T](
    description: String = "",
    options: List[T],
    displayFunc: T => String,
    default: List[T] = Nil,
    minSelection: Option[Int] = Some(1),
    maxSelection: Option[Int] = Some(1))
    extends DialogModel[T]
