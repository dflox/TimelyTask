package me.timelytask.view.views

import me.timelytask.view.viewmodel.dialogmodel.DialogModel

trait DialogFactory[RenderType] {
  def apply(dialogModel: Option[DialogModel[?]], currentView: Option[RenderType])
  : Option[Dialog[?, RenderType]]
}

