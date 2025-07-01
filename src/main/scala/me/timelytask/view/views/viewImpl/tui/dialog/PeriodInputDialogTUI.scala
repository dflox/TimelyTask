package me.timelytask.view.views.viewImpl.tui.dialog

import com.github.nscala_time.time.Imports.Period
import me.timelytask.view.viewmodel.dialogmodel.InputDialogModel
import org.jline.terminal.Terminal

// TODO: Implement the PeriodInputDialogTUI
class PeriodInputDialogTUI(
    override val dialogModel: Option[InputDialogModel[Period]],
    override val currentView: Option[String],
    override val terminal: Terminal)
    extends TUIDialog[Period] {

  override def apply(): Option[Period] =
    None // Placeholder for PeriodInputDialogTUI
}
