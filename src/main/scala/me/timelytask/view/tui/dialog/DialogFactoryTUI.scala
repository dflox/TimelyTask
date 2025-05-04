package me.timelytask.view.tui.dialog

import com.github.nscala_time.time.Imports.DateTime
import me.timelytask.view.tui.dialog.*
import me.timelytask.view.viewmodel.dialogmodel.*
import me.timelytask.view.views.{Dialog, DialogFactory}
import org.jline.terminal.Terminal

class DialogFactoryTUI(terminal: Terminal) extends DialogFactory[String] {
  override def apply(dialogModel: Option[DialogModel[?]], currentView: Option[String])
  : Option[Dialog[?, String]] = {
    dialogModel match {
      case dialogModel: Option[OptionDialogModel[_]] => Some(OptionDialogTUI(dialogModel,
        currentView, terminal))
      case dialogModel: Option[ConfirmDialogModel] => Some(ConfirmDialogTUI(dialogModel,
        currentView, terminal))
      case dialogModel: Option[InputDialogModel[String]] => Some(TextInputDialogTUI(dialogModel,
        currentView, terminal))
      case dialogModel: Option[InputDialogModel[DateTime]] => Some(DateInputDialogTUI(dialogModel,
        currentView, terminal))
      case _ => None
    }
  }
}
