package me.timelytask.view.tui.dialog

import me.timelytask.view.tui.UtilTUI.{createLine, cutText}
import me.timelytask.view.viewmodel.dialogmodel.{DialogModel, OptionDialogModel}
import org.jline.terminal.Terminal

class OptionDialogTUI[T](val dialogModel: DialogModel, val terminal: Terminal) extends TUIDialog {

  override def getUserInput: T = {
    val optionDialogModel = dialogModel.asInstanceOf[OptionDialogModel[T]]
    if (optionDialogModel.options.length > 9) {
      throw new IllegalArgumentException("OptionDialogTUI only supports up to 9 options currently")
    }

    val options = optionDialogModel.options
    val terminalWidth = terminal.getWidth
    val dialogString = createDialogString(options, terminalWidth)
    val viewWithDialog = overlapString(optionDialogModel.currentView, dialogString)
    terminal.writer().println(viewWithDialog)

    val keyMap = createCustomKeyMap(options)
    var userInput = -1
    while (!keyMap.contains(userInput)) {
      userInput = terminal.reader().read().toChar.asDigit - 1
    }
    keyMap(userInput)
  }

  private def createDialogString(options: List[T], terminalWidth: Int): String = {
    val stringBuilder = new StringBuilder()
    stringBuilder.append(createLine(terminalWidth) + "\n")
    stringBuilder.append(cutText(s"Please select an option (1-${options.length}):", terminalWidth) +
      "\n")
    for (i <- options.indices) {
      stringBuilder.append(cutText(s"(${i + 1}) " + options(i).toString, terminalWidth) + "\n")
    }
    stringBuilder.append(createLine(terminalWidth))
    stringBuilder.toString()
  }

  private def createCustomKeyMap(options: List[T]): Map[Int, T] = {
    options.indices.map(i => (i, options(i))).toMap
  }

}
