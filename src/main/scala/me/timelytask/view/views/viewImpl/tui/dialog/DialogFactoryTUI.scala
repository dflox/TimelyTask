package me.timelytask.view.views.viewImpl.tui.dialog

import com.github.nscala_time.time.Imports.DateTime
import me.timelytask.view.viewmodel.dialogmodel.*
import me.timelytask.view.views.viewImpl.tui.dialog.{ConfirmDialogTUI, DateInputDialogTUI}
import me.timelytask.view.views.{Dialog, DialogFactory}
import org.jline.terminal.Terminal

class DialogFactoryTUI(terminal: Terminal) extends DialogFactory[String] {
  override def apply(dialogModel: Option[DialogModel[?]], currentView: Option[String])
  : Option[Dialog[?, String]] = {
    dialogModel.map {
      case dialogModel: OptionDialogModel[?] => OptionDialogTUI(Some(dialogModel),
        currentView, terminal)
      case dialogModel: ConfirmDialogModel => ConfirmDialogTUI(Some(dialogModel),
        currentView, terminal)
      case dialogModel: InputDialogModelString => TextInputDialogTUI(Some(dialogModel),
        currentView, terminal)
      case dialogModel: InputDialogModelDateTime => DateInputDialogTUI(Some(dialogModel),
        currentView, terminal)
      case dialogModel: InputDialogModelPeriod => ???
    }
  }
}
