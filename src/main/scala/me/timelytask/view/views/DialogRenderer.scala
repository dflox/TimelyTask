package me.timelytask.view.views

import me.timelytask.view.viewmodel.dialogmodel.DialogModel

trait DialogRenderer[RenderType, ValueType, DialogModelType <: DialogModel[ValueType]] {
  def apply(dialogModel: Option[DialogModelType], currentView: Option[RenderType])
  : Option[ValueType]
}

