package me.timelytask.view.viewmodel.dialogmodel

case class OptionDialogModel[T](options: List[T], currentView: String) extends DialogModel(){
}
