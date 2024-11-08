package me.timelytask.controller

import me.timelytask.view.tui.dialog.*
import me.timelytask.view.viewmodel.dialogmodel.*
import org.jline.terminal.Terminal

class DialogFactory(terminal: Terminal) {
  def createDialog(dialogModel: DialogModel): TUIDialog = {
    dialogModel match {
      case dialogModel: OptionDialogModel[_] => new OptionDialogTUI(dialogModel, terminal)
      case dialogModel: ConfirmDialogModel => new ConfirmDialogTUI(dialogModel, terminal)
      case dialogModel: TextInputDialogModel => new TextInputDialogTUI(dialogModel, terminal)
      case dialogModel: DateInputDialogModel => new DateInputDialogTUI(dialogModel, terminal)
    }
  }
}
