package me.timelytask.view.views.viewImpl.gui.dialog

import me.timelytask.view.viewmodel.dialogmodel.DialogModel
import me.timelytask.view.views.{Dialog, DialogFactory}
import scalafx.scene.Scene

class DialogFactoryImpl extends DialogFactory[Scene] {
  override def apply(dialogModel: Option[DialogModel[?]], currentView: Option[Scene]): Option[Dialog[?, Scene]] = {
    None
  }
}