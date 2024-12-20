package me.timelytask.view.viewmodel.elemts

import me.timelytask.view.viewmodel.dialogmodel.DialogModel

trait Focusable[T] {
  val dialogModel: DialogModel[T]
  val description: String
//  def interact[RenderType](getInput: (DialogModel[T], RenderType) => Option[T],
//                           currentView: RenderType)
//  : Option[T] = {
//    getInput(dialogModel, currentView)
//  }
}
